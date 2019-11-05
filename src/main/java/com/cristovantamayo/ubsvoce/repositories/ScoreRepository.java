package com.cristovantamayo.ubsvoce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cristovantamayo.ubsvoce.entities.Score;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

}
