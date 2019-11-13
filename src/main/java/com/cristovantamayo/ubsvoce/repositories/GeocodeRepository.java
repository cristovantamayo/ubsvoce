package com.cristovantamayo.ubsvoce.repositories;
/**
 *  Repositório para Geocode implementando JpaRepository<Geocode>.
 */
//import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cristovantamayo.ubsvoce.entities.Geocode;

@Repository
public interface GeocodeRepository extends JpaRepository<Geocode, Long> {
	
	// A funcao foi comentada pois funcionalidade poe ela predendida foi transferida para UnidadeRepository
	
	/*@Query("SELECT g FROM Geocode g WHERE ROUND(g.geocode_lat) = ?1 AND ROUND(g.geocode_long) = ?2")
	List<Geocode> findPartialByLatAndLng(Double lat, Double lng);*/
}
