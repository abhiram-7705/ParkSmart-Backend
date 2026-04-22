package com.cts.mfrp.parksmart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.mfrp.parksmart.model.ParkingSpaces;

@Repository
public interface ParkingSpacesRepository extends JpaRepository<ParkingSpaces, Integer>{
	
	List<String> findByNameStartingWith(String query);
}
