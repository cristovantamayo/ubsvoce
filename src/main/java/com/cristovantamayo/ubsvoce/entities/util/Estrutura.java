package com.cristovantamayo.ubsvoce.entities.util;

import java.util.List;

import com.cristovantamayo.ubsvoce.entities.Unidade;

public class Estrutura {
	
	private String address;
	private Coods geocode;
	private Integer current_page;
	private Integer per_page;
	private long total_entries;
	private List<Unidade> entries;
	
	public Estrutura(String address, Double lat, Double lng, Integer current_page, Integer per_page, long total_entries, List<Unidade> entries) {
		this.address = address;
		this.geocode = new Coods(lat, lng);
		this.current_page = current_page;
		this.per_page = per_page;
		this.total_entries = total_entries;
		this.entries = entries;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public Coods getGeocode() {
		return geocode;
	}

	public void setGeocode(Double lat, Double lng) {
		this.geocode = new Coods(lat, lng);
	}

	public Integer getCurrent_page() {
		return current_page;
	}

	public void setCurrent_page(Integer current_page) {
		this.current_page = current_page;
	}

	public Integer getPer_page() {
		return per_page;
	}

	public void setPer_page(Integer per_page) {
		this.per_page = per_page;
	}

	public long getTotal_entries() {
		return total_entries;
	}

	public void setTotal_entries(long total_entries) {
		this.total_entries = total_entries;
	}

	public List<Unidade> getEntries() {
		return entries;
	}

	public void setEntries(List<Unidade> entries) {
		this.entries = entries;
	}

}