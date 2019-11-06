package com.cristovantamayo.ubsvoce.controllers.rest;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cristovantamayo.ubsvoce.entities.Geocode;
import com.cristovantamayo.ubsvoce.entities.Unidade;
import com.cristovantamayo.ubsvoce.entities.util.Estrutura;
import com.cristovantamayo.ubsvoce.repositories.GeocodeRepository;
import com.cristovantamayo.ubsvoce.services.GeocodingService;
import com.cristovantamayo.ubsvoce.services.UnidadeService;

@RestController

@RequestMapping("/")
public class UnidadeResourceAPI {
	
	@Autowired
	UnidadeService service;
	
	@Autowired
	GeocodeRepository repo;
	
	@Autowired
	GeocodingService geo;
	

	@RequestMapping(path="/", method=RequestMethod.GET)
	public ResponseEntity<?> all() {
		
		List<Geocode> listaGeocodes = geo.retrieveNearUbs("Rua Elías Berbare, 112 - Vila Marly, Taubaté - SP");
		return ResponseEntity.ok().body(listaGeocodes);
	}
	
	@RequestMapping(path="/find_ubs", method=RequestMethod.GET)
	public ResponseEntity<?> find_ubs(
			@RequestParam String query,
			@RequestParam Integer page,
			@RequestParam Integer per_page) {
		
		Unidade unidade = service.find(1);
		Estrutura estrutura = new Estrutura(1, 1, 37690, Arrays.asList(unidade));
		
		return ResponseEntity.ok().body(estrutura);
	}
	
}
