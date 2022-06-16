package com.pichincha.prueba.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pichincha.prueba.demo.dto.ResponseDto;
import com.pichincha.prueba.demo.dto.StoreDto;

@RestController
@RequestMapping("/store")
public class StoreController {

	@PostMapping()
	public ResponseEntity<ResponseDto> createStore(@RequestBody StoreDto store) {
		return null;
	}
}
