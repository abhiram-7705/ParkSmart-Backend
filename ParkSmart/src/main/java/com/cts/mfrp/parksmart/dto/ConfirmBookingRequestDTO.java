package com.cts.mfrp.parksmart.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ConfirmBookingRequestDTO {

    @NotBlank
    private String holdId;

    @NotNull
    private Integer spaceId;

    @NotNull
    private LocalDateTime arrival;

    @NotNull
    private LocalDateTime leaving;

    private String promoCode;

    @NotBlank
    private String vehicleNumber;

	public String getHoldId() {
		return holdId;
	}

	public void setHoldId(String holdId) {
		this.holdId = holdId;
	}

	public Integer getSpaceId() {
		return spaceId;
	}

	public void setSpaceId(Integer spaceId) {
		this.spaceId = spaceId;
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

	public String getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
    
    
}
