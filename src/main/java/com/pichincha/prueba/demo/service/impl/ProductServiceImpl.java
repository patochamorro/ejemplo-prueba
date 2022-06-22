package com.pichincha.prueba.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pichincha.prueba.demo.entity.Product;
import com.pichincha.prueba.demo.entity.ProductWs;
import com.pichincha.prueba.demo.entity.ResponseWs;
import com.pichincha.prueba.demo.entity.Store;
import com.pichincha.prueba.demo.entity.StoreStock;
import com.pichincha.prueba.demo.exception.StoreNotFoundException;
import com.pichincha.prueba.demo.repository.ProductRepository;
import com.pichincha.prueba.demo.repository.StoreRepository;
import com.pichincha.prueba.demo.repository.StoreStockRepository;
import com.pichincha.prueba.demo.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private StoreRepository storeRepository;
	@Autowired
	private StoreStockRepository storeStockRepository;

	public List<ProductWs> findAll() {
		RestTemplate restTemplate = new RestTemplate();
		String baseUrl = "http://demo2595538.mockable.io";
		baseUrl = baseUrl.concat("/store/products/");
		Object response = restTemplate.getForEntity(baseUrl, ProductWs.class);
		return null;
	}

	@Override
	public void loadProductsFromWS(Long storeId) throws StoreNotFoundException {
		Store store = storeRepository.findById(storeId)
				.orElseThrow(() -> new StoreNotFoundException(storeId.toString()));

		RestTemplate restTemplate = new RestTemplate();
		String baseUrl = "http://demo2595538.mockable.io/products";
		ResponseEntity<ResponseWs> response = restTemplate.getForEntity(baseUrl, ResponseWs.class);

		List<ProductWs> products = response.getBody().getProducts().stream().filter(product -> product.getStock() > 5)
				.collect(Collectors.toList());

		List<ProductWs> productsToSaveInDb = products.stream()
				.filter(product -> productRepository.findByCode(product.getCod()) == null).collect(Collectors.toList());

		productsToSaveInDb.stream().forEach(product -> productRepository
				.save(new Product(null, product.getCod(), product.getName(), product.getPrice(), null)));

		List<Product> productsInDb = products.stream().map(product -> productRepository.findByCode(product.getCod()))
				.collect(Collectors.toList());

		List<StoreStock> stock = productsInDb.stream()
				.map(product -> storeStockRepository.findStockByStoreAndproduct(product.getId(), store.getId()))
				.collect(Collectors.toList()).stream().filter(p -> p != null).collect(Collectors.toList());
		
		
		

	}
}
