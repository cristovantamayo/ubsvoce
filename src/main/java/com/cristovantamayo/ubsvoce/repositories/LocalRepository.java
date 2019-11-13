package com.cristovantamayo.ubsvoce.repositories;
/**
 *  Repositório para Geocode implementando JpaRepository<Local>.
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cristovantamayo.ubsvoce.entities.Local;

@Repository
public interface LocalRepository extends JpaRepository<Local, Long> {

}
