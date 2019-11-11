package com.cristovantamayo.ubsvoce.entities;
/**
 * Entidade JPA Geocode
 * Represensa uma UBS
 * 		<Long> 		id
 * 		<Double> 	geocode_lat
 * 		<Double> 	geocode_long
 * 		<Unidade> 	unidade {@code @OneToOne} {@see Unidade}
*/
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "unidade_geocode")
public class Geocode implements Serializable {
	
	private static final long serialVersionUID = -2674507603069383842L;
	
	@Id
	private Long id;
	
	@JsonProperty(value="lat") // Alias no JSON
	@Column(precision=7, scale=2)
	private Double geocode_lat;

	@JsonProperty(value="long")
	@Column(precision=7, scale=2)
	private Double geocode_long;
	
	
	@JsonBackReference // Evita que haja uma conversão recursiva das informações da Unidade na saida da API
	@MapsId
	@OneToOne
	@JoinColumn(name="unidade_id")
	private Unidade unidade;
	
	public Geocode() {}
	
	public Geocode(Double geocode_lat, Double geocode_long) {
		this.geocode_lat = geocode_lat;
		this.geocode_long = geocode_long;
	}
	
	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public Double getGeocode_lat() {
		return geocode_lat;
	}

	public void setGeocode_lat(Double geocode_lat) {
		this.geocode_lat = geocode_lat;
	}

	public Double getGeocode_long() {
		return geocode_long;
	}

	public void setGeocode_long(Double geocode_long) {
		this.geocode_long = geocode_long;
	}
	
}
