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

import com.cristovantamayo.ubsvoce.entities.Geocode;
import com.cristovantamayo.ubsvoce.entities.Local;
import com.cristovantamayo.ubsvoce.repositories.GeocodeRepository;
import com.cristovantamayo.ubsvoce.repositories.LocalRepository;
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
	GeocodeRepository geocodeRepository;
		
	/**
	 * 
	 * @param address
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
	
	public List<Geocode> retrieveNearUbs(String address){
		
		// Buscando resultados do Google Geocoding API
		GeocodingResult[] results = GeocodingService.searchAddress(address, apiKey);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		System.out.println(gson.toJson(results));
		// Extrair os dados necessários 
		Double lat = Double.parseDouble(gson.toJson(results[0].geometry.location.lat));
		Double lng = Double.parseDouble(gson.toJson(results[0].geometry.location.lng));
		String formatedAddress = gson.toJson(results[0].formattedAddress);
		String ubsName = gson.toJson(results[0].formattedAddress);
		
		/** 
		 * TODO Salvar os dados para posterior implementação de busca em banco
		 */
		saveLocation(formatedAddress, lat, lng);
		
		List<Geocode> lista = geocodeRepository.findPartialByLatAndLng((double) Math.round(lat), (double) Math.round(lng));
		
		Stream<Geocode> stream = lista.stream();
		List<Geocode> novaLista = stream.filter(item -> isNear(item, lat, lng)).collect(Collectors.toList());
		
		return novaLista;
	}
	
	public static boolean isNear(Geocode item, Double lat, Double lng) {
		Double distancia = calculaDistancia(lat, lng, item.getGeocode_lat(), item.getGeocode_long()) ; //distance(lat, item.getGeocode_lat(), lng, item.getGeocode_long(), 800, 1200);
		return 1500 > distancia;
	}
	
	
	private static double calculaDistancia(double lat1, double lng1, double lat2, double lng2) {
	    //double earthRadius = 3958.75;//miles
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

	private void saveLocation(String formatedAddress, Double lat, Double lng) {
		Date searched_at = null;
		localRepository.save(new Local(formatedAddress, lat, lng, searched_at));
	}

}
