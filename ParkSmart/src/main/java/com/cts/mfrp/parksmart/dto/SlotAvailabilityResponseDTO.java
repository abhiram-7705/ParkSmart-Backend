package com.cts.mfrp.parksmart.dto;

import java.util.List;

public class SlotAvailabilityResponseDTO {

    private List<SlotDTO> slots;

    private int durationMinutes;
    private int billedHours;
    private double totalPrice;
    
    
	public List<SlotDTO> getSlots() {
		return slots;
	}
	public void setSlots(List<SlotDTO> slots) {
		this.slots = slots;
	}
	public int getDurationMinutes() {
		return durationMinutes;
	}
	public void setDurationMinutes(int durationMinutes) {
		this.durationMinutes = durationMinutes;
	}
	public int getBilledHours() {
		return billedHours;
	}
	public void setBilledHours(int billedHours) {
		this.billedHours = billedHours;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

    
}