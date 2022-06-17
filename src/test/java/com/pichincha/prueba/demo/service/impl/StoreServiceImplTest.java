package com.pichincha.prueba.demo.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import com.pichincha.prueba.demo.dto.ResponseDto;
import com.pichincha.prueba.demo.dto.StoreDto;
import com.pichincha.prueba.demo.entity.Store;
import com.pichincha.prueba.demo.exception.StoreException;
import com.pichincha.prueba.demo.repository.StoreRepository;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class StoreServiceImplTest {

	@MockBean
	private StoreRepository storeRepository;

	@InjectMocks
	private StoreServiceImpl storeService;

	private StoreDto storeDto;

	@BeforeEach
	private void initialize() {
		storeDto = new StoreDto();
		storeDto.setCategory("legumbres");
		storeDto.setName("Tienda de prueba");
		storeDto.setOwner("pepe");
	}

	@Test
	public void givenStoreWithouStock_whenSaveAStoreRequest_thenSaveStoreInDb() throws StoreException {

		Mockito.when(storeRepository.save(Mockito.any(Store.class)))
				.thenReturn(new Store(1L, "mock", null, null, null));
		ResponseDto responseDto = storeService.saveStore(storeDto);
		Assert.hasText(responseDto.getMessage(), "No se guardó en la BDD");
	}

	@Test()
	public void givenStoreWithouStock_whenSaveAStoreRequestAndBddProblems_thenThrowsStoreException() {

		Mockito.when(storeRepository.save(Mockito.any(Store.class)))
				.thenThrow(new RuntimeException("Something wrong with Database"));
		StoreException exception = null;
		try {
			storeService.saveStore(storeDto);
		} catch (StoreException e) {
			exception = e;
		}
		Assert.notNull(exception, "No se produjo la excepcion esperada StoreException");

	}
}
