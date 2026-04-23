package com.cts.mfrp.parksmart.dto;

import jakarta.validation.constraints.NotNull;

public class CancelBookingRequestDTO {
	
	@NotNull(message = "Booking ID is required")
    private Integer bookingId;

	public Integer getBookingId() {
		return bookingId;
	}

	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}
	
	

}
