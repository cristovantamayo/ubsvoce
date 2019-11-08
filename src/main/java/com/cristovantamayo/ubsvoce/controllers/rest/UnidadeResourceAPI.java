package com.cristovantamayo.ubsvoce.controllers.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.cristovantamayo.ubsvoce.entities.Unidade;
import com.cristovantamayo.ubsvoce.entities.util.Estrutura;
import com.cristovantamayo.ubsvoce.repositories.GeocodeRepository;
import com.cristovantamayo.ubsvoce.services.GeocodingService;
import com.cristovantamayo.ubsvoce.services.UnidadeService;

@RestController

@RequestMapping("/v1")
public class UnidadeResourceAPI {
	
	@Autowired
	UnidadeService service;
	
	@Autowired
	GeocodeRepository repo;
	
	@Autowired
	GeocodingService geo;
	
	@RequestMapping(path="/find_ubs", method=RequestMethod.GET)
	public ResponseEntity<?> find_ubs(
			@RequestParam String address,
			@RequestParam Integer page,
			@RequestParam Integer per_page) {
		
		// restringindo a busca para o raio de 3000 metros: Um bairro grande.
		List<Unidade> entries = geo.retrieveNearUbs(address, 3000.0);
		
		/**
		 * Estrutura a ser traduzinda em JSON como response da API.
		 */
		Estrutura estrutura = new Estrutura(
				address,
				page,			// Page
				per_page, 		// Per_page
				entries.size(), // total_entries
				entries			// entries
		);
		
		return ResponseEntity.ok().body(estrutura);
	}
	
}
