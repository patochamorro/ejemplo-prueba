package com.pichincha.prueba.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pichincha.prueba.demo.dto.StoreDto;
import com.pichincha.prueba.demo.entity.Store;
import com.pichincha.prueba.demo.repository.StoreRepository;
import com.pichincha.prueba.demo.service.StoreService;

@Service
public class StoreServiceImpl implements StoreService {

	@Autowired
	private StoreRepository storeRepository; 
	
	@Override
	public boolean saveStore(StoreDto storeDto) {
		
		Store storeBdd = new Store();
		storeBdd.setCategory(storeDto.getCategory());
		storeBdd.setName(storeDto.getName());
		storeBdd.setOwner(storeDto.getOwner());		
		storeRepository.save(storeBdd);
		return false;
	}

}
