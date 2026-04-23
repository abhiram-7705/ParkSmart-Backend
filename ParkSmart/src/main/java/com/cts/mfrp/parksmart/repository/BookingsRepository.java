package com.cts.mfrp.parksmart.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cts.mfrp.parksmart.model.Bookings;

@Repository
public interface BookingsRepository extends JpaRepository<Bookings, Integer> {

	boolean existsBySlotIdAndArrivalLessThanAndLeavingGreaterThan(
	        Integer slotId,
	        LocalDateTime leaving,
	        LocalDateTime arrival
	);
	
	@Query("""
		    SELECT ps.name
		    FROM Bookings b
		    JOIN b.parkingSlot s
		    JOIN s.parkingSpace ps
		    WHERE b.user.email = :email
		    AND (
		        LOWER(ps.name) LIKE LOWER(CONCAT('%', :query, '%')) OR
		        LOWER(ps.location) LIKE LOWER(CONCAT('%', :query, '%')) OR
		        LOWER(ps.city) LIKE LOWER(CONCAT('%', :query, '%'))
		    )
		""")
	List<String> findSuggestionsByUserAndQuery(String email, String query);

	List<Bookings> findByUserId(int userId);
	
}
