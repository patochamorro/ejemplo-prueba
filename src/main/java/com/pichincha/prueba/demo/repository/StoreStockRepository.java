package com.pichincha.prueba.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pichincha.prueba.demo.entity.StoreStock;

public interface StoreStockRepository extends JpaRepository<StoreStock, Long> {

	@Query(value = "select * from store_stock where product_id = :productId and store_id = :storeId", nativeQuery = true)
	StoreStock findStockByStoreAndproduct(Long storeId, Long productId);

}
