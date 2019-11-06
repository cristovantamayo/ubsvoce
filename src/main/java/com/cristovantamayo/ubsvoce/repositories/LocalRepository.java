package com.cristovantamayo.ubsvoce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cristovantamayo.ubsvoce.entities.Local;

@Repository
public interface LocalRepository extends JpaRepository<Local, Long> {

}
