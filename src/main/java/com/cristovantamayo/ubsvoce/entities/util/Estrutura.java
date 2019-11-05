package com.cristovantamayo.ubsvoce.entities.util;

import java.util.List;

import com.cristovantamayo.ubsvoce.entities.Unidade;

public class Estrutura {
	
	
	private Integer current_page;
	private Integer per_page;
	private Integer total_entries;
	private List<Unidade> entries;
	
	public Estrutura(Integer current_page, Integer per_page, Integer total_entries, List<Unidade> entries) {
		this.current_page = current_page;
		this.per_page = per_page;
		this.total_entries = total_entries;
		this.entries = entries;
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

	public Integer getTotal_entries() {
		return total_entries;
	}

	public void setTotal_entries(Integer total_entries) {
		this.total_entries = total_entries;
	}

	public List<Unidade> getEntries() {
		return entries;
	}

	public void setEntries(List<Unidade> entries) {
		this.entries = entries;
	}

}