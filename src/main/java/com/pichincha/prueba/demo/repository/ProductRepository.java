package com.pichincha.prueba.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pichincha.prueba.demo.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	Product findByCode(String code);
}
