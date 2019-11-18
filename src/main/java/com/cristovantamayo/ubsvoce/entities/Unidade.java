package com.cristovantamayo.ubsvoce.entities;
/**
 * Entidade JPA Unidade
 * Represensa uma UBS
 * 		<Long> 		id
 * 		<String> 	name
 * 		<String> 	city
 * 		<String> 	neighborhod
 * 		<String> 	telefone
 * 		<Geocode> 	geocode			{@code @OneToOne} {@see Geocode}
 * 		<Score> 	score			{@code @OneToOne} {@see Score}
 * 		<Double> 	distance		--> Transient
 */
import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonManagedReference;

// Anota o 'bean' como uma entidade JPA
@Entity
// Define o nome da tabale no banco
@Table(name = "unidade")
public class Unidade implements Serializable {
	
	// Requisito Java Bean 
	private static final long serialVersionUID = -517095946271188908L;

	// anota o Id JPA setando AUTO_INCREMENT
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String address;
	private String city;
	private String neighborhood;
	private String phone;
	
	/**
	 * Atributo nao persistido
	 * distance sera atribuido no processo de Geolocalizacao @see GeocodingService 
	 */
	@Transient
	private Double distance;
	
	/**
	 * Relacionamento OneToOne Bidirecional:
	 * https://developer.jboss.org/wiki/EntendendoMappedByDoHibernate
	 */
	@JsonManagedReference
	@OneToOne(cascade = CascadeType.ALL,  mappedBy = "unidade")
	private Geocode geocode;

	@JsonManagedReference
	@OneToOne(cascade = CascadeType.ALL,  mappedBy = "unidade")
	private Score score;
	
	
	//////////////// CONSTRUTORES
	
	public Unidade() {}
	
	public Unidade(String name, String address, String neighborhood, String city, String phone, Geocode geocode, Score score) {
		this.name = name;
		this.address = address;
		this.neighborhood = neighborhood;
		this.city = city;
		this.phone = phone;
		this.geocode = geocode;
		this.score = score;
	}
	
	public Unidade(Unidade unidade, Double distance) {
		this.id = unidade.getId();
		this.name = unidade.getName();
		this.address = unidade.getAddress();
		this.neighborhood = unidade.getNeighborhood();
		this.city = unidade.getCity();
		this.phone = unidade.getPhone();
		this.geocode = unidade.getGeocode();
		this.score = unidade.getScore();
		this.distance = distance;
	}

	//////////////// GETTER & SETTERS

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getNeighborhood() {
		return neighborhood;
	}
	
	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Geocode getGeocode() {
		return geocode;
	}

	public void setGeocode(Geocode geocode) {
		
		this.geocode = geocode;
	}

	public Score getScore() {
		return score;
	}

	public void setScore(Score score) {
		this.score = score;
	}
	
	public Double getDistance() {
		return distance;
	}
	
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	
	/////////////// HASHCODE & EQUALS // Tornar Comparável
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Unidade other = (Unidade) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
	
	
}

