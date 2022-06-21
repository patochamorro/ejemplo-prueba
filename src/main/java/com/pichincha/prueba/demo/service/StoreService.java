package com.pichincha.prueba.demo.service;

import com.pichincha.prueba.demo.dto.ResponseDto;
import com.pichincha.prueba.demo.dto.StoreDto;
import com.pichincha.prueba.demo.exception.StoreException;
import com.pichincha.prueba.demo.exception.StoreNotFoundException;

public interface StoreService {

	ResponseDto saveStore(StoreDto storeDto) throws StoreException;

	StoreDto findStoreByName(String storeName) throws StoreNotFoundException;
	
	Boolean updateStore(StoreDto storeDto, Long storeId) throws StoreNotFoundException;
}
