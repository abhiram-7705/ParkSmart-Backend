package com.cts.mfrp.parksmart.dto;

import java.time.LocalDateTime;
import java.util.List;

public class BookingSummaryResponseDTO {

    private Integer spaceId;
    private List<Integer> slotIds;

    private LocalDateTime arrival;
    private LocalDateTime leaving;

    private double baseRate;
    private int durationHours;

    private double subtotal;
    private double tax;
    private double total;

    private double walletBalance;
    private boolean sufficientBalance;
    private LocalDateTime holdExpiry;

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

	public double getBaseRate() {
		return baseRate;
	}

	public void setBaseRate(double baseRate) {
		this.baseRate = baseRate;
	}

	public int getDurationHours() {
		return durationHours;
	}

	public void setDurationHours(int durationHours) {
		this.durationHours = durationHours;
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

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getWalletBalance() {
		return walletBalance;
	}

	public void setWalletBalance(double walletBalance) {
		this.walletBalance = walletBalance;
	}

	public LocalDateTime getHoldExpiry() {
		return holdExpiry;
	}

	public void setHoldExpiry(LocalDateTime holdExpiry) {
		this.holdExpiry = holdExpiry;
	}

	public boolean isSufficientBalance() {
		return sufficientBalance;
	}

	public void setSufficientBalance(boolean sufficientBalance) {
		this.sufficientBalance = sufficientBalance;
	}
    
    
}