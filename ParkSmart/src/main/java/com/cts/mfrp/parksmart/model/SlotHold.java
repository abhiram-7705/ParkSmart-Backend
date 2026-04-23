package com.cts.mfrp.parksmart.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class SlotHold {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer holdId;

    private String holdGroupId;

    private LocalDateTime arrival;
    private LocalDateTime leaving;

    private LocalDateTime expiresAt;

    @ManyToOne
    @JoinColumn(name = "slot_id")
    private ParkingSlots slot;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
    
    private LocalDateTime createdAt;
    
    private boolean extended;

	public Integer getHoldId() {
		return holdId;
	}

	public void setHoldId(Integer holdId) {
		this.holdId = holdId;
	}

	public String getHoldGroupId() {
		return holdGroupId;
	}

	public void setHoldGroupId(String holdGroupId) {
		this.holdGroupId = holdGroupId;
	}

	public LocalDateTime getArrival() {
		return arrival;
	}

	public void setArrival(LocalDateTime arrival) {
		this.arrival = arrival;
	}

	public LocalDateTime getLeaving() {
		return leaving;
	}

	public void setLeaving(LocalDateTime leaving) {
		this.leaving = leaving;
	}

	public LocalDateTime getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(LocalDateTime expiresAt) {
		this.expiresAt = expiresAt;
	}

	public ParkingSlots getSlot() {
		return slot;
	}

	public void setSlot(ParkingSlots slot) {
		this.slot = slot;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public boolean isExtended() {
		return extended;
	}

	public void setExtended(boolean extended) {
		this.extended = extended;
	}
    
	
    
}
