package com.cristovantamayo.ubsvoce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cristovantamayo.ubsvoce.entities.Geocode;

@Repository
public interface GeocodeRepository extends JpaRepository<Geocode, Long> {

}
