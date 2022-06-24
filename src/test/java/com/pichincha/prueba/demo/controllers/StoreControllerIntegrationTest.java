package com.pichincha.prueba.demo.controllers;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collections;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;

import com.pichincha.prueba.demo.dto.StoreDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StoreControllerIntegrationTest {

	@LocalServerPort
	private int port;

	private String baseUrl = "http://localhost";
	private static RestTemplate restTemplate = null;

	@BeforeAll
	public static void init() {
		restTemplate = new RestTemplate();
	}

	@BeforeEach
	public void setUp() {
		baseUrl = baseUrl.concat(":").concat(port + "").concat("/store");
	}

	@Test
	@Sql(statements = "insert into store (name , category , owner) values ('Peps Store2', 'legumbres','pepe')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "delete FROM store", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void givenAValidStoreName_whenFindStore_thenReturnStoreInfo() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.set("storeName", "Peps Store2");
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		ResponseEntity<StoreDto> storeResponse = restTemplate.exchange(baseUrl, HttpMethod.GET, entity, StoreDto.class);
		StoreDto store = storeResponse.getBody();
		assertAll(() -> assertNotNull(store, "Object was null"),
				() -> assertEquals("Peps Store2", store.getName(), "The name not match"));
	}

	@Test
	@Sql(statements = "insert into store (name , category , owner) values ('Peps Store2', 'legumbres','pepe')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(statements = "delete FROM store", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void givenAInvalidStoreName_whenFindStore_thenReturnError() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.TEXT_PLAIN));
		headers.set("storeName", "Peps Store");
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		try {
			ResponseEntity<String> storeResponse = restTemplate.exchange(baseUrl, HttpMethod.GET, entity, String.class);
			assertAll(() -> assertNotNull(storeResponse, "Object was null"), () -> assertEquals(HttpStatus.NOT_FOUND,
					storeResponse.getStatusCode(), "The error code was diferent"));
		} catch (Exception e) {
			// TODO: handle exception
			assertAll(() -> assertEquals(e.getMessage(), "404 : \"Tienda 'Peps Store' no encontrada\"",
					"The error code was diferent"));
		}

	}

}
