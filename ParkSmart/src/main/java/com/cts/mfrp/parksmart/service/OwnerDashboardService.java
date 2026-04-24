package com.cts.mfrp.parksmart.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.mfrp.parksmart.dto.OwnerSlotDTO;
import com.cts.mfrp.parksmart.dto.ParkingCardDTO;
import com.cts.mfrp.parksmart.model.ParkingSlots;
import com.cts.mfrp.parksmart.model.ParkingSpaces;
import com.cts.mfrp.parksmart.model.Reviews;
import com.cts.mfrp.parksmart.model.Users;
import com.cts.mfrp.parksmart.repository.ParkingSlotsRepository;
import com.cts.mfrp.parksmart.repository.ParkingSpacesRepository;
import com.cts.mfrp.parksmart.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class OwnerDashboardService {
	

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ParkingSpacesRepository parkingSpaceRepository;
	@Autowired
	private ParkingSlotsRepository parkingSlotsRepository;

    public List<ParkingCardDTO> getParkingCardsForOwner(String email) {

		Users owner = userRepository.findByEmail(email)
				.orElseThrow(() -> new IllegalArgumentException("User not found"));

		List<ParkingSpaces> spaces = parkingSpaceRepository.findByOwnerId(owner.getUserId());

		if (spaces.isEmpty()) {
			return Collections.emptyList();
		}

		return spaces.stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	private ParkingCardDTO mapToDTO(ParkingSpaces space) {
		
		ParkingCardDTO dto = new ParkingCardDTO();
		dto.setSpaceId(space.getSpaceId());
		dto.setName(space.getName());
		dto.setType(space.getType());
		dto.setLocation(space.getLocation());
		dto.setCity(space.getCity());
		dto.setPricePerHour(space.getPricePerHour());
		dto.setLatitude(space.getLatitude());
		dto.setLongitude(space.getLongitude());
		dto.setDistance(0.0);
		
		List<String> facilities = new java.util.ArrayList<>();
		
		if (space.isCctv()) 
			facilities.add("cctv");
		if (space.isEvCharging()) 
			facilities.add("ev");
		if (space.isGuarded()) 
			facilities.add("guarded");
		if (space.isCoveredFence()) 
			facilities.add("covered");
		
		dto.setFacilities(facilities);
		
		List<Reviews> reviews = space.getReviews();
		double avgRating = 0.0;
		int totalRatings = 0;

		if (reviews != null && !reviews.isEmpty()) {

		    totalRatings = reviews.size();

		    double sum = 0.0;
		    for (Reviews r : reviews) {
		        sum += r.getRating();
		    }

		    avgRating = sum / totalRatings;
		}

		dto.setRating(avgRating);
		dto.setCountRating(totalRatings);
		
		
		return dto;
	}
	
	public List<OwnerSlotDTO> getSlotsForOwnerSpace(String email, Integer spaceId) {

	    Users owner = userRepository.findByEmail(email)
	            .orElseThrow(() ->
	                    new IllegalArgumentException("User not found"));

	    ParkingSpaces space = parkingSpaceRepository
	            .findBySpaceIdAndOwnerId(spaceId, owner.getUserId())
	            .orElseThrow(() ->
	                    new IllegalArgumentException("Unauthorized space access"));

	    List<ParkingSlots> slots =
	            parkingSlotsRepository.findBySpaceId(space.getSpaceId());
	    if (slots == null || slots.isEmpty()) {
	        return Collections.emptyList();
	    }

	    return slots.stream()
	            .map(slot -> {

	                OwnerSlotDTO dto = new OwnerSlotDTO();
	                dto.setSlotId(slot.getSlotId());
	                dto.setSlotNumber(slot.getSlotNumber());
	                dto.setStatus(slot.getStatus());
	                return dto;
	            })
	            .collect(Collectors.toList());
	}
	
	@Transactional
	public void toggleSlotStatus(String email, Integer slotId) {

		Users owner = userRepository.findByEmail(email)
				.orElseThrow(() -> new IllegalArgumentException("User not found"));

		ParkingSlots slot = parkingSlotsRepository.findById(slotId)
				.orElseThrow(() -> new IllegalArgumentException("Slot not found"));

		ParkingSpaces space = slot.getParkingSpace();
		if (space.getOwner().getUserId()!=owner.getUserId()) {
			throw new IllegalArgumentException("Unauthorized slot access");
		}

		String currentStatus = slot.getStatus();

		if ("OCCUPIED".equalsIgnoreCase(currentStatus)) {
			throw new IllegalStateException("Occupied slot cannot be blocked or freed");
		}

		if ("FREE".equalsIgnoreCase(currentStatus)) {
			slot.setStatus("BLOCKED");
		} else if ("BLOCKED".equalsIgnoreCase(currentStatus)) {
			slot.setStatus("FREE");
		} else {
			throw new IllegalStateException("Invalid slot status");
		}
	}

}
