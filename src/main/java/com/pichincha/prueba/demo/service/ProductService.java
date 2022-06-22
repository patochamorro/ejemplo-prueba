package com.pichincha.prueba.demo.service;

import java.util.List;

import com.pichincha.prueba.demo.entity.ProductWs;
import com.pichincha.prueba.demo.exception.StoreNotFoundException;

public interface ProductService {

	List<ProductWs> findAll();

	void loadProductsFromWS(Long storeId) throws StoreNotFoundException;
}
