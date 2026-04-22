package com.cts.mfrp.parksmart.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.mfrp.parksmart.repository.ParkingSpacesRepository;

@Service
public class SearchService {

	@Autowired
	private ParkingSpacesRepository parkingSpaceRepository;
	

	public List<String> getLocationSuggestions(String query) {
	        if (query.length() < 2) {
	            return Collections.emptyList();
	        }
	
	        List<String> rawResults =
	                parkingSpaceRepository.findByNameStartingWith(query.toLowerCase());
	
	        return rawResults.stream()
	                .distinct()
	                .limit(10)
	                .collect(Collectors.toList());
	    }

	
}
