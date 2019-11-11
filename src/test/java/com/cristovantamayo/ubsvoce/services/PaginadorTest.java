package com.cristovantamayo.ubsvoce.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.cristovantamayo.ubsvoce.services.util.Paginador;

public class PaginadorTest {
	
	@Test
	public void deveCorresponderAListaParginada() {
		List<Integer> lista = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14 ,15, 16, 17, 18);
		assertThat(
			Paginador.ofSize(lista, 6),
			is( Arrays.asList(
					Arrays.asList(1, 2, 3, 4, 5, 6),
					Arrays.asList(7, 8, 9, 10, 11, 12),
					Arrays.asList(13, 14, 15, 16, 17, 18)
					)
			)
		);
	}
	
	@Test
	public void deveCorresponderASubListaParginada() {
		List<Integer> lista = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14 ,15, 16, 17, 18);
			Paginador listas = Paginador.ofSize(lista, 6);
			assertThat(
					listas.get(0),
					is(Arrays.asList(1, 2, 3, 4, 5, 6))
					);
			
		
	}
	
	@Test
	public void deveRetornarONumeroDeSubListas() {
		List<Integer> lista = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14 ,15, 16, 17, 18);
		Paginador listas = Paginador.ofSize(lista, 6);
		assertEquals(3, listas.size());
	}
}
