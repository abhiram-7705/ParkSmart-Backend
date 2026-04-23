package com.cts.mfrp.parksmart.dto;

import java.util.List;

import com.cts.mfrp.parksmart.model.ParkingSpaces;
import com.cts.mfrp.parksmart.model.SlotHold;

public class BookingContext {

    private List<SlotHold> holds;
    private List<Integer> slotIds;
    private ParkingSpaces space;
    private int hours;
    private double baseRate;
	public List<SlotHold> getHolds() {
		return holds;
	}
	public void setHolds(List<SlotHold> holds) {
		this.holds = holds;
	}
	public List<Integer> getSlotIds() {
		return slotIds;
	}
	public void setSlotIds(List<Integer> slotIds) {
		this.slotIds = slotIds;
	}
	public ParkingSpaces getSpace() {
		return space;
	}
	public void setSpace(ParkingSpaces space) {
		this.space = space;
	}
	public int getHours() {
		return hours;
	}
	public void setHours(int hours) {
		this.hours = hours;
	}
	public double getBaseRate() {
		return baseRate;
	}
	public void setBaseRate(double baseRate) {
		this.baseRate = baseRate;
	}
    
    
	
}
