package com.cristovantamayo.ubsvoce.entities;
/**
 * Entidadde JPA Local
 * TODO Em desenvolvimento
 * Visa armazenar as consultas realizadas e formar um banco de Geocode
 * para que sejam realizadas consulas de Geocodding locais, baixando assim os custo com as requisições à API do Google.
 */
import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Locais")
public class Local implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String formatedAddress;
	private Double lat;
	private Double lng;
	
	public Local() {
		
	}

	public Local(String formatedAddress, Double lat, Double lng, Date searched_at) {
		this.formatedAddress = formatedAddress;
		this.lat = lat;
		this.lng = lng;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFormatedAddress() {
		return formatedAddress;
	}

	public void setFormatedAddress(String formatedAddress) {
		this.formatedAddress = formatedAddress;
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
		Local other = (Local) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	
}
