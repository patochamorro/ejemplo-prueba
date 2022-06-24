package com.pichincha.prueba.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.pichincha.prueba.demo.dto.ProductWs;
import com.pichincha.prueba.demo.dto.ResponseWs;
import com.pichincha.prueba.demo.entity.Product;
import com.pichincha.prueba.demo.entity.Store;
import com.pichincha.prueba.demo.entity.StoreStock;
import com.pichincha.prueba.demo.exception.StoreNotFoundException;
import com.pichincha.prueba.demo.repository.ProductRepository;
import com.pichincha.prueba.demo.repository.StoreRepository;
import com.pichincha.prueba.demo.repository.StoreStockRepository;
import com.pichincha.prueba.demo.service.ProductService;
import com.pichincha.prueba.demo.util.Configs;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private StoreRepository storeRepository;
	@Autowired
	private StoreStockRepository storeStockRepository;
	@Autowired
	private Configs config;

	@Override
	@Transactional
	public void loadProductsFromWS(Long storeId) throws StoreNotFoundException {

		Store store = storeRepository.findById(storeId)
				.orElseThrow(() -> new StoreNotFoundException(storeId.toString()));

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<ResponseWs> response = restTemplate.getForEntity(config.getUrl(), ResponseWs.class);

		List<ProductWs> products = response.getBody().getProducts().stream().filter(product -> product.getStock() > 5)
				.collect(Collectors.toList());

		List<ProductWs> productsToSaveInDb = products.stream()
				.filter(product -> productRepository.findByCode(product.getCod()) == null).collect(Collectors.toList());

		productsToSaveInDb.stream().forEach(product -> productRepository
				.save(new Product(null, product.getCod(), product.getName(), product.getPrice(), null)));

		List<StoreStock> productsInDb = products
				.stream().map(product -> new StoreStock(null, product.getPrice(),
						productRepository.findByCode(product.getCod()), store, product.getStock()))
				.collect(Collectors.toList());

		List<StoreStock> stockToUpdate = productsInDb.stream().map(stock -> {
			StoreStock productStok = storeStockRepository.findStockByStoreAndproduct(store.getId(),
					stock.getProductOwner().getId());
			if (productStok != null) {
				productStok.setStock(stock.getStock());
			} else {
				productStok = stock;
			}
			return productStok;
		}).collect(Collectors.toList());

		storeStockRepository.saveAll(stockToUpdate);

		log.info("Store update for store: " + store.toString());

	}
}
