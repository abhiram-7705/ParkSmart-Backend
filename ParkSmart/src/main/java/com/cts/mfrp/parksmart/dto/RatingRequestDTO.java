package com.cts.mfrp.parksmart.dto;

import jakarta.validation.constraints.*;

public class RatingRequestDTO {

    @NotNull(message = "Booking ID is required")
    private Integer bookingId;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Minimum rating is 1")
    @Max(value = 5, message = "Maximum rating is 5")
    private Double rating;

    @Size(max = 255, message = "Comment cannot exceed 255 characters")
    private String comment;

	public Integer getBookingId() {
		return bookingId;
	}

	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

    
}