package com.cts.mfrp.parksmart.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class SlotHoldRequestDTO {

    private Integer spaceId;
    @NotEmpty(message = "At least one slot must be selected")
    private List<Integer> slotIds;

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
	public List<Integer> getSlotIds() {
		return slotIds;
	}
	public void setSlotIds(List<Integer> slotIds) {
		this.slotIds = slotIds;
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