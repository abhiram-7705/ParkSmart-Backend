package com.cts.mfrp.parksmart.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.mfrp.parksmart.model.SlotHold;

@Repository
public interface SlotHoldRepository extends JpaRepository<SlotHold, Integer> {
	
	boolean existsBySlot_SlotIdAndArrivalLessThanAndLeavingGreaterThanAndExpiresAtAfter(
	        Integer slotId,
	        LocalDateTime leaving,
	        LocalDateTime arrival,
	        LocalDateTime now
	);
	List<SlotHold> findByHoldGroupId(String holdGroupId);
	
	void deleteByHoldGroupIdAndUser_Email(String holdGroupId, String email);
	
	void deleteByExpiresAtBefore(LocalDateTime time);
	
}
