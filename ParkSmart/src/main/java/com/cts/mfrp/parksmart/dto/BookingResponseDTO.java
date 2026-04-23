package com.cts.mfrp.parksmart.dto;

import java.util.List;

public class BookingResponseDTO {
	private List<Integer> bookingIds;
    private String status;
	public List<Integer> getBookingIds() {
		return bookingIds;
	}
	public void setBookingIds(List<Integer> bookingIds) {
		this.bookingIds = bookingIds;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
    
    
}
