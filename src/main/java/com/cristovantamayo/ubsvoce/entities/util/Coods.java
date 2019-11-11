package com.cristovantamayo.ubsvoce.entities.util;
/**
 * Class auxiliar para estruturar a resposta da API fornecendo um parce de Geocoordenadas o que simplifica e facilita consideravelmete a estruturação da resposta JSON
 * A Classes é utiliza para representar o ponto central de pesquisa, que não está ligado diretamente a qualquer UBS mas transporta as Geocoodenadas receitas da API do Google.
 * @author Cristovan
 *
 */
public class Coods{
	private Double lat;
	private Double lng;
	
	public Coods(Double lat, Double lng){
		this.lat=lat;
		this.lng=lng;
	}

	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public Double getLng() {
		return lng;
	}
	public void setLng(Double lng) {
		this.lng = lng;
	}
}
