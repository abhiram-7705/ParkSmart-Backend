package com.cts.mfrp.parksmart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.mfrp.parksmart.model.ParkingSlots;

@Repository
public interface ParkingSlotsRepository extends JpaRepository<ParkingSlots, Integer> {

	List<ParkingSlots> findBySpaceId(Integer spaceId);

}
