package com.pichincha.prueba.demo.exception;

import lombok.Getter;

@Getter
public class StoreNotFoundException extends Exception {

	private static final long serialVersionUID = 3464435239371242653L;

	private String storeName;

	public StoreNotFoundException(String storeName) {
		super("Tienda '"+storeName +"' no encontrada");
		this.storeName = storeName;
		
	}
}
