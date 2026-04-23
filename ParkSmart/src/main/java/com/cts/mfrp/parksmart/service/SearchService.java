package com.cts.mfrp.parksmart.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.mfrp.parksmart.dto.ParkingCardDTO;
import com.cts.mfrp.parksmart.dto.SpaceSearchRequestDTO;
import com.cts.mfrp.parksmart.model.ParkingSlots;
import com.cts.mfrp.parksmart.model.ParkingSpaces;
import com.cts.mfrp.parksmart.model.Reviews;
import com.cts.mfrp.parksmart.repository.BookingsRepository;
import com.cts.mfrp.parksmart.repository.ParkingSlotsRepository;
import com.cts.mfrp.parksmart.repository.ParkingSpacesRepository;

@Service
public class SearchService {

	@Autowired
	private ParkingSpacesRepository parkingSpaceRepository;
	@Autowired
	private ParkingSlotsRepository parkingSlotsRepository;
	@Autowired
	private BookingsRepository bookingsRepository;

	public List<String> getLocationSuggestions(String query) {
	        if (query.length() < 2) {
	            return Collections.emptyList();
	        }
	
	        List<String> rawResults =
	                parkingSpaceRepository.findLocationSuggestions(query.toLowerCase());
	
	        return rawResults.stream()
	                .distinct()
	                .limit(10)
	                .collect(Collectors.toList());
	    }
	
	private ParkingCardDTO mapToDTO(ParkingSpaces space, double userLat, double userLng) {
		
		ParkingCardDTO dto = new ParkingCardDTO();
		dto.setSpaceId(space.getSpaceId());
		dto.setName(space.getName());
		dto.setType(space.getType());
		dto.setLocation(space.getLocation());
		dto.setCity(space.getCity());
		dto.setPricePerHour(space.getPricePerHour());
		dto.setLatitude(space.getLatitude());
		dto.setLongitude(space.getLongitude());
		dto.setDistance(calculateDistance(userLat, userLng, space.getLatitude(), space.getLongitude()));
		
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

	public List<ParkingCardDTO> searchSpaces(SpaceSearchRequestDTO request) {

	    double[] coords = resolveLocation(request.getLocation());
	    double userLat = coords[0];
	    double userLng = coords[1];

	    LocalDateTime[] time = resolveTime(request);
	    LocalDateTime arrival = time[0];
	    LocalDateTime leaving = time[1];

	    List<ParkingSpaces> spaces = parkingSpaceRepository.findAll();
	    
	    if (request.getMinPrice() != null && request.getMaxPrice() != null) {
	        if (request.getMinPrice() > request.getMaxPrice()) {
	            throw new IllegalArgumentException("Min price cannot be greater than max price");
	        }
	    }
	    Double radiusValue = request.getRadius();
	    if (radiusValue == null) {
	        radiusValue = 5.0;
	    } else if (radiusValue <= 0) {
	        throw new IllegalArgumentException("Radius must be greater than 0");
	    }
	    final double radius = radiusValue;
	    
	    List<ParkingCardDTO> result = spaces.stream()
	            .map(space -> mapToDTO(space, userLat, userLng))
	            .filter(dto -> dto.getDistance() <= radius)
	            .filter(dto -> isSpaceAvailable(dto.getSpaceId(), arrival, leaving))
	            .filter(dto -> filterByType(dto, request.getTypes()))
	            .filter(dto -> filterByPrice(dto, request.getMinPrice(), request.getMaxPrice()))
	            .filter(dto -> filterByFacilities(dto, request.getAmenities()))
	            .collect(Collectors.toList());
	    
	    sortResults(result, request.getSortBy());
	    
	    return result;
	}
	
	private LocalDateTime[] resolveTime(SpaceSearchRequestDTO request) {

	    LocalDateTime now = LocalDateTime.now().plusMinutes(15);

	    LocalDateTime arrival;
	    LocalDateTime leaving;

	    if (request.getArrival() == null) {
	        arrival = now;
	        leaving = arrival.plusHours(1);
	    } else {
	        arrival = LocalDateTime.parse(request.getArrival());
	        leaving = LocalDateTime.parse(request.getLeaving());

	        if (!leaving.isAfter(arrival)) {
	            throw new IllegalArgumentException("Leaving time must be after arrival time");
	        }
	        if (arrival.isBefore(now)) {
	            throw new IllegalArgumentException("Arrival must be at least 15 minutes from now");
	        }
	    }

	    return new LocalDateTime[]{arrival, leaving};
	}
	
	private double[] resolveLocation(String location) {

	    if (location == null || location.isBlank()) {
	        throw new IllegalArgumentException("Location is required");
	    }

	    String[] parts = location.split(",");

	    String loc = parts[0].trim();
	    String city = parts.length > 1 ? parts[1].trim() : "";

	    ParkingSpaces space =
	            parkingSpaceRepository.findFirstByLocationIgnoreCaseAndCityIgnoreCase(loc, city);

	    if (space == null) {
	        throw new IllegalArgumentException("No parking spaces found in this location");
	    }

	    return new double[]{
	            space.getLatitude(),
	            space.getLongitude()
	    };
	}
	
	


	private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {

		final int R = 6371;
		double dLat = Math.toRadians(lat2 - lat1);
		double dLon = Math.toRadians(lon2 - lon1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return R * c;
	}
	
	private boolean isSpaceAvailable(Integer spaceId, LocalDateTime arrival, LocalDateTime leaving) {

		List<ParkingSlots> slots = parkingSlotsRepository.findBySpaceId(spaceId);

		for (ParkingSlots slot : slots) {

	        boolean booked = bookingsRepository.existsBySlotIdAndArrivalLessThanAndLeavingGreaterThan(
	                        slot.getSlotId(),
	                        leaving,
	                        arrival
	                );
	        if (!booked) {
	            return true;
	        }
		}
		return false;
	}
	
	private boolean filterByType(ParkingCardDTO dto, List<String> types) {
	    if (types == null || types.isEmpty()) {
	        return true;
	    }
	    return types.stream()
	            .map(String::toLowerCase)
	            .anyMatch(t -> t.equals(dto.getType().toLowerCase()));
	}
	
	private boolean filterByPrice(ParkingCardDTO dto, Double minPrice, Double maxPrice) {

		double price = dto.getPricePerHour();

		if (minPrice != null && price < minPrice) {
			return false;
		}

		if (maxPrice != null && price > maxPrice) {
			return false;
		}

		return true;
	}
	
	private boolean filterByFacilities(ParkingCardDTO dto, List<String> facilities) {
		if (facilities == null || facilities.isEmpty()) {
			return true;
		}

		return dto.getFacilities().containsAll(facilities);
	}
	
	private void sortResults(List<ParkingCardDTO> list, String sortBy) {

	    if (sortBy == null) {
	        return;
	    }

	    switch (sortBy.toLowerCase()) {

	        case "distance":
	            list.sort(Comparator.comparing(ParkingCardDTO::getDistance));
	            break;

	        case "price_low":
	            list.sort(Comparator.comparing(ParkingCardDTO::getPricePerHour));
	            break;

	        case "price_high":
	            list.sort(Comparator.comparing(ParkingCardDTO::getPricePerHour).reversed());
	            break;

	        case "rating":
	            list.sort(Comparator.comparing(ParkingCardDTO::getRating).reversed());
	            break;
	    }
	}
	
}
