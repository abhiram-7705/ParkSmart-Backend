package com.cts.mfrp.parksmart.dto;

import jakarta.validation.constraints.NotBlank;

public class SlotAvailabilityRequestDTO {

    private Integer spaceId;
    
    @NotBlank(message = "Arrival time is required")
    private String arrival;
    
    @NotBlank(message = "Leaving time is required")
    private String leaving;
	public Integer getSpaceId() {
		return spaceId;
	}
	public void setSpaceId(Integer spaceId) {
		this.spaceId = spaceId;
	}
	public String getArrival() {
		return arrival;
	}
	public void setArrival(String arrival) {
		this.arrival = arrival;
	}
	public String getLeaving() {
		return leaving;
	}
	public void setLeaving(String leaving) {
		this.leaving = leaving;
	}

    
}