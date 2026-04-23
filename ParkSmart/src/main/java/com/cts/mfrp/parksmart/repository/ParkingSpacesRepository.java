package com.cts.mfrp.parksmart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cts.mfrp.parksmart.model.ParkingSpaces;

@Repository
public interface ParkingSpacesRepository extends JpaRepository<ParkingSpaces, Integer>{
	
	@Query("""
		    SELECT DISTINCT CONCAT(p.location, ', ', p.city)
		    FROM ParkingSpaces p
		    WHERE LOWER(p.location) LIKE LOWER(CONCAT('%', :query, '%'))
		       OR LOWER(p.city) LIKE LOWER(CONCAT('%', :query, '%'))
		""")
		List<String> findLocationSuggestions(@Param("query") String query);

	ParkingSpaces findFirstByLocationIgnoreCaseAndCityIgnoreCase(String loc, String city);
}
