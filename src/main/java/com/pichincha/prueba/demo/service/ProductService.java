package com.pichincha.prueba.demo.service;

import com.pichincha.prueba.demo.exception.StoreNotFoundException;

public interface ProductService {

	void loadProductsFromWS(Long storeId) throws StoreNotFoundException;
}
