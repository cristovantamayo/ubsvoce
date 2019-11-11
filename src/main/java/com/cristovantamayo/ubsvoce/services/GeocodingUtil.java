package com.cristovantamayo.ubsvoce.services;
/**
 * Class ontendo o modelo matemáticos para o calculo em pares de Geocoordenadas
 */
import com.cristovantamayo.ubsvoce.entities.Unidade;

class GeocodingUtil{
	
	public GeocodingUtil() {
		
	}
	
	/**
	 * O metodo é constituido por um script matemático dispoível na comunidade para calculo de distancias entre dois pontos de Geocoordenadas.
	 * @param <Double> lat1
	 * @param <Double> lng1
	 * @param <Double> lat2
	 * @param <Double> lng2
	 * @return <Double> distancia  (em metros)
	 */
	double calculaDistancia(double lat1, double lng1, double lat2, double lng2) {
	   
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
	 * Predicado para filtro por 'proximidade calculada' utilizado em {@see GeocodingService}
	 * @param <Unidade>	unidade
	 * @param <double> lat, 
	 * @param <double> lng
	 * @param <Integer>	raioDeAcao  (em metros)
	 * @return <boolean>
	 */
	boolean isNear(Unidade unidade, Double lat, Double lng, Double raioDeAcao) {
		Double distancia = calculaDistancia(lat, lng, unidade.getGeocode().getGeocode_lat(), unidade.getGeocode().getGeocode_long());
		return raioDeAcao > distancia;
	}
}
