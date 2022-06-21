package com.pichincha.prueba.demo.service.impl;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pichincha.prueba.demo.dto.ResponseDto;
import com.pichincha.prueba.demo.dto.StoreDto;
import com.pichincha.prueba.demo.entity.Store;
import com.pichincha.prueba.demo.exception.StoreException;
import com.pichincha.prueba.demo.exception.StoreNotFoundException;
import com.pichincha.prueba.demo.repository.StoreRepository;
import com.pichincha.prueba.demo.service.StoreService;

@Service
public class StoreServiceImpl implements StoreService {

	@Autowired
	private StoreRepository storeRepository;

	@Override
	public ResponseDto saveStore(StoreDto storeDto) throws StoreException {

		Store storeBdd = new Store();
		storeBdd.setCategory(storeDto.getCategory());
		storeBdd.setName(storeDto.getName());
		storeBdd.setOwner(storeDto.getOwner());

		try {
			Store savedStore = storeRepository.save(storeBdd);
			return new ResponseDto("Store saved: " + savedStore.getId());
		} catch (Exception e) {
			throw new StoreException("No se puede guardar en la BDD", e);
		}

	}

	@Override
	public StoreDto findStoreByName(String storeName) throws StoreNotFoundException {
		Store store = storeRepository.findByName(storeName);
		if (Objects.isNull(store)) {
			throw new StoreNotFoundException(storeName);
		}

		return new StoreDto(store.getId(), store.getName(), store.getCategory(), store.getOwner(), null);
	}

	@Override
	public Boolean updateStore(StoreDto storeDto, Long storeId) throws StoreNotFoundException {

		boolean isUpdated;

		Optional<Store> storeFindInBdd = storeRepository.findById(storeId);

		if (storeFindInBdd.isPresent()) {
			Store storeBdd = storeFindInBdd.get();
			storeBdd.setCategory(storeDto.getCategory());
			storeBdd.setName(storeDto.getName());
			storeBdd.setOwner(storeDto.getOwner());

			storeRepository.save(storeBdd);
			isUpdated = true;
		} else {
			throw new StoreNotFoundException(storeId.toString());
		}

		return isUpdated;
	}

}
