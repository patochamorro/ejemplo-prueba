package com.pichincha.prueba.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductWs {

	private Long id;
	private String cod;
	private String name;
	private Double price;
	private Integer stock;

}
