package com.pichincha.prueba.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pichincha.prueba.demo.exception.StoreNotFoundException;
import com.pichincha.prueba.demo.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@PostMapping("/stockByStore/{storeId}")
	public ResponseEntity<?> loadProductStore(@PathVariable Long storeId) {
		try {
			productService.loadProductsFromWS(storeId);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (StoreNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}
}
