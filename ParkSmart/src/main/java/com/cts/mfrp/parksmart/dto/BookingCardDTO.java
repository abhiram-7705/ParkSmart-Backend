package com.cts.mfrp.parksmart.dto;

import java.time.LocalDateTime;

public class BookingCardDTO {

    private Integer bookingId;

    private String spaceName;
    private String city;
    private String spaceType;

    private String slotNumber;

    private LocalDateTime arrival;
    private LocalDateTime leaving;

    private Double totalAmount;
    private Double pricePerHour;

    private String status;
    
    private Double latitude;
    private Double longitude;

    private Boolean isCancelable;
    private Boolean isExtendable;
    private Boolean isContactAllowed;
    private Boolean isRatingAllowed;
    private Boolean isReceiptAvailable;
	public Integer getBookingId() {
		return bookingId;
	}
	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}
	public String getSpaceName() {
		return spaceName;
	}
	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getSpaceType() {
		return spaceType;
	}
	public void setSpaceType(String spaceType) {
		this.spaceType = spaceType;
	}
	public String getSlotNumber() {
		return slotNumber;
	}
	public void setSlotNumber(String slotNumber) {
		this.slotNumber = slotNumber;
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
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Double getPricePerHour() {
		return pricePerHour;
	}
	public void setPricePerHour(Double pricePerHour) {
		this.pricePerHour = pricePerHour;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Boolean getIsCancelable() {
		return isCancelable;
	}
	public void setIsCancelable(Boolean isCancelable) {
		this.isCancelable = isCancelable;
	}
	public Boolean getIsExtendable() {
		return isExtendable;
	}
	public void setIsExtendable(Boolean isExtendable) {
		this.isExtendable = isExtendable;
	}
	public Boolean getIsContactAllowed() {
		return isContactAllowed;
	}
	public void setIsContactAllowed(Boolean isContactAllowed) {
		this.isContactAllowed = isContactAllowed;
	}
	public Boolean getIsRatingAllowed() {
		return isRatingAllowed;
	}
	public void setIsRatingAllowed(Boolean isRatingAllowed) {
		this.isRatingAllowed = isRatingAllowed;
	}
	public Boolean getIsReceiptAvailable() {
		return isReceiptAvailable;
	}
	public void setIsReceiptAvailable(Boolean isReceiptAvailable) {
		this.isReceiptAvailable = isReceiptAvailable;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

    
}