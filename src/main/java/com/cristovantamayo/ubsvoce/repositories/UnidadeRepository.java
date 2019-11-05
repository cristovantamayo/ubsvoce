package com.cristovantamayo.ubsvoce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cristovantamayo.ubsvoce.entities.Unidade;

@Repository
public interface UnidadeRepository extends JpaRepository<Unidade, Long> {

}