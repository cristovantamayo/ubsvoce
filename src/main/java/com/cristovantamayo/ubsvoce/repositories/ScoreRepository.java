package com.cristovantamayo.ubsvoce.repositories;
/**
 *  Repositório para Geocode implementando JpaRepository<Score>.
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cristovantamayo.ubsvoce.entities.Score;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

}
