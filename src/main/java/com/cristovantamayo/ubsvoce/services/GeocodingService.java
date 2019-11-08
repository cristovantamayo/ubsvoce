package com.cristovantamayo.ubsvoce.services;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import com.cristovantamayo.ubsvoce.entities.Local;
import com.cristovantamayo.ubsvoce.entities.Unidade;
import com.cristovantamayo.ubsvoce.repositories.LocalRepository;
import com.cristovantamayo.ubsvoce.repositories.UnidadeRepository;
import com.cristovantamayo.ubsvoce.services.exceptions.GeocodingServiceException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;

@Service
@EnableConfigurationProperties
@ConfigurationProperties("geocoding")
public class GeocodingService {
	
	// Google API Key
	private String apiKey;
	
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	
	@Autowired
	LocalRepository localRepository;
	
	@Autowired
	UnidadeRepository unidadeRepository;
		
	/**
	 * EntryPoint do Enredeço a ser pesquisado na Google Geocoding API
	 * Uma vez recuperada a pesquisa processa a lista de UBS previamente importada,
	 * filtrando 
	 * @param <String> address   			--> Endereço digitado 
	 * @params <Double> raioDeAcao 			--> raio da área de cobertura (em Metros), distância o ponto centrar às suas extremidades 
	 * @return <List<Unidade>> novaLista	--> Unidade dentro área de cobertura
	 */
	public List<Unidade> retrieveNearUbs(String address, Double raioDeAcao){
		
		// Busca resultados do Google Geocoding API
		GeocodingResult[] results = GeocodingService.searchAddress(address, apiKey);
		
		// Trata o resultado
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		System.out.println(gson.toJson(results));
		// Extrai os dados relevantes
		Double lat = Double.parseDouble(gson.toJson(results[0].geometry.location.lat));	// Latitide
		Double lng = Double.parseDouble(gson.toJson(results[0].geometry.location.lng)); // Longitude
		String formatedAddress = gson.toJson(results[0].formattedAddress);				// Endereço Formatado
		
		/** 
		 * TODO Salva os dados para posterior implementação de busca em banco
		 */
		saveLocation(formatedAddress, lat, lng);
		
		// Primeiro filtro que realiza uma pré busca por coordenadas compatíveis com a porção inteira das coordenadas, restringir o range.
		List<Unidade> lista = unidadeRepository.findPartialByLatAndLng((double) Math.round(lat), (double) Math.round(lng));

		
		// segundo filtro: comparação de proximidade. Somente as Unidade
		// Programação funcional que utiliza o recurso de stream para formar um pipeline com o predicado isNear. Ao final a lista é reestabelecida.
		Stream<Unidade> stream = lista.stream();
		List<Unidade> novaLista = stream.filter(itemEmTeste -> isNear(itemEmTeste, lat, lng, raioDeAcao)).collect(Collectors.toList());
		
		return novaLista;
	}
	
	/**
	 * Faz a requisição à API do Google buscando pelo endereço <address>
	 * Script fornecido pelo Google: https://github.com/googlemaps/google-maps-services-java
	 * @param address
	 * @param apiKey --> Credencial Geocoding API Google
	 * @return
	 */
	public static GeocodingResult[] searchAddress(String address, String apiKey) {
		GeoApiContext context = new GeoApiContext.Builder()
			    .apiKey(apiKey)
			    .build();
			GeocodingResult[] results;
			try {
				results = GeocodingApi.geocode(context,
				    address).await();
				return results;
			} catch (ApiException | InterruptedException | IOException e) {
				throw new GeocodingServiceException(e.getMessage());
			}
	}
	
	/**
	 * Predicado do filtro de proximidade
	 * @param <Unidade>	item
	 * @param <double> lat
	 * @param <double> lng
	 * @param <Integer>	raioDeAcao
	 * @return <boolean>
	 */
	
	public static boolean isNear(Unidade item, Double lat, Double lng, Double raioDeAcao) {
		Double distancia = calculaDistancia(lat, lng, item.getGeocode().getGeocode_lat(), item.getGeocode().getGeocode_long());
		return raioDeAcao > distancia;
	}
	
	/**
	 * Script matemático dispoível na comunidade para calculo de distancias entre dois pontos de Geocoordenadas.
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return <Doule> distancia .. em metros
	 */
	private static double calculaDistancia(double lat1, double lng1, double lat2, double lng2) {
	   
	    double earthRadius = 6371;//kilometers
	    double dLat = Math.toRadians(lat2 - lat1);
	    double dLng = Math.toRadians(lng2 - lng1);
	    double sindLat = Math.sin(dLat / 2);
	    double sindLng = Math.sin(dLng / 2);
	    double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
	            * Math.cos(Math.toRadians(lat1))
	            * Math.cos(Math.toRadians(lat2));
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double dist = earthRadius * c;
	 
	    return dist * 1000; //em metros
	}
	
	/**
	 * Salva em Banco a localização pesquisada
	 * Uma vez registrada não será necessária a requisição ao serviços do Google.
	 * TODO recurso não totalmente implemnetado.
	 * @param formatedAddress
	 * @param lat
	 * @param lng
	 */

	private void saveLocation(String formatedAddress, Double lat, Double lng) {
		Date searched_at = null;
		localRepository.save(new Local(formatedAddress, lat, lng, searched_at));
	}
	

}
