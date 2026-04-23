package com.cts.mfrp.parksmart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.mfrp.parksmart.model.Reviews;

@Repository
public interface ReviewsRepository extends JpaRepository<Reviews, Integer> {

	boolean existsByBookingId(Integer bookingId);

}
