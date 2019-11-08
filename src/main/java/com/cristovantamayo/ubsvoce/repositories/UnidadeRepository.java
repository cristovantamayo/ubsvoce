package com.cristovantamayo.ubsvoce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cristovantamayo.ubsvoce.entities.Unidade;

@Repository
public interface UnidadeRepository extends JpaRepository<Unidade, Long> {

	@Query("SELECT u FROM Unidade u WHERE ROUND(u.geocode.geocode_lat) = ?1 AND ROUND(u.geocode.geocode_long) = ?2")
	List<Unidade> findPartialByLatAndLng(Double lat, Double lng);
}