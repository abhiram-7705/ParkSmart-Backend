package com.cts.mfrp.parksmart.dto;

import java.time.LocalDateTime;
import java.util.List;

public class InvoiceResponseDTO {
	
    private String spaceName;
    private String location;

    private List<String> slotNumbers;

    private LocalDateTime arrival;
    private LocalDateTime leaving;
    private int durationHours;

    private String vehicleNumber;

    private double baseRate;
    private double subtotal;
    private double tax;
    private double discount;
    private double totalAmount;

    private String status;

	public String getSpaceName() {
		return spaceName;
	}

	public void setSpaceName(String spaceName) {
		this.spaceName = spaceName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public List<String> getSlotNumbers() {
		return slotNumbers;
	}

	public void setSlotNumbers(List<String> slotNumbers) {
		this.slotNumbers = slotNumbers;
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

	public int getDurationHours() {
		return durationHours;
	}

	public void setDurationHours(int durationHours) {
		this.durationHours = durationHours;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public double getBaseRate() {
		return baseRate;
	}

	public void setBaseRate(double baseRate) {
		this.baseRate = baseRate;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
    

}
