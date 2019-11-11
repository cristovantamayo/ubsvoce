package com.cristovantamayo.ubsvoce.controllers.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cristovantamayo.ubsvoce.entities.Unidade;
import com.cristovantamayo.ubsvoce.entities.util.Estrutura;
import com.cristovantamayo.ubsvoce.repositories.GeocodeRepository;
import com.cristovantamayo.ubsvoce.services.GeocodingService;
import com.cristovantamayo.ubsvoce.services.UnidadeService;
import com.google.maps.model.GeocodingResult;

import net.bytebuddy.implementation.bytecode.constant.DefaultValue;
/**
 * Endpoint da API de UBSVocê
 * @author Cristovan
 *
 */
@RestController

@RequestMapping("/v1")
@EnableConfigurationProperties
@ConfigurationProperties("geocoding")
public class UnidadeResourceAPI {

	// Google API Key
	private String apiKey;

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	
	@Autowired
	UnidadeService service;
	
	@Autowired
	GeocodeRepository repo;
	
	@Autowired
	GeocodingService geo;
	/**
	 * Entrypoint de Pesquisa da API UBSVoce, forneça um endereço válido
	 * 
	 * @param <String> address			-	Endereço digitado pelo usuárop
	 * @param <Double> radius			-	Raio de pesquisa em metros do ponto central, opcional, default 3000.0
	 * @param <Integer> page			-	Indice da página quando em paginação, opcional, default 1
	 * @param <Integer> per_page		-	Número de Unidade retornadas por página, opcional, default 50
	 * @return <body>  
	 */									
	@RequestMapping(path="/find_ubs", method=RequestMethod.GET)
	public ResponseEntity<?> find_ubs(
			@RequestParam String address,
			@RequestParam(value="radius", required = false, defaultValue = "3000") String radiusString,
			@RequestParam(value="page", required = false, defaultValue = "1") String pageString,
			@RequestParam(value="per_page", required = false, defaultValue = "50") String per_pageString) {
		
		// Converter os parametros opicionais para seus esperados tipos.
		Double radius = Double.parseDouble(radiusString);
		Integer page = Integer.parseInt(pageString);
		Integer per_page = Integer.parseInt(per_pageString);
		
		// Busca resultados no Google Geocoding API
		GeocodingResult[] results = GeocodingService.searchAddress(address, apiKey);
		
		// Estrutura a ser traduzinda em JSON no response da API.
		Estrutura estrutura;
		
		// Validar os resultados fornecidos.
		// Uma pesquesa pode não encontrar correspondencias e retornar nulo ou vazio 
		if(results != null && results.length > 0) {
		
			//Recuperar dados retornados do Google para compor estrutura de resposta da API
			String formattedAddress = results[0].formattedAddress;
			Double lat = results[0].geometry.location.lat;
			Double lng = results[0].geometry.location.lng;
				
			// restringindo a busca para o raio estipulado
			List<Unidade> entries = geo.retrieveNearUbs(lat, lng, radius, page, per_page);	
				
			// Instanciando a estrutura	
			estrutura = new Estrutura(
				formattedAddress,	// Endereço formatado
				lat,				// Latitude para o Endereço pesquisado
				lng,				// Longitude para o Endereço pesquisado
				page,				// Page
				per_page, 			// Per_page
				entries!=null ? entries.size() : 0, 	// total_entries
				entries!=null ? entries : new ArrayList<Unidade>()				// entries
			);
			
		} else {
			
			// Retorno amigavel
			estrutura = null;
		}
			
	
		return ResponseEntity.ok().body(estrutura);
	}
	
}
