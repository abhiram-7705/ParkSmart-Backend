package com.cts.mfrp.parksmart.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Bookings {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "booking_id")
	private int bookingId;
	
	@Column(name = "vehicle_number")
	private String vehicleNumber;
	
	@Column(name = "arrival")
	private LocalDateTime arrival;
	
	@Column(name = "leaving")
	private LocalDateTime leaving;
	
	@Column(name = "base_rate")
	private double baseRate;
	
	@Column(name = "subtotal")
	private double subtotal;
	
	@Column(name = "tax")
	private double tax;
	
	@Column(name = "total_amount")
	private double totalAmount;
	
	@Column(name = "due_amount")
	private double dueAmount;
	
	@Column(name = "promo_code")
	private String promocode;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "payment_status")
	private String paymentStatus;
	
	@Column(name = "is_refundable")
	private boolean isRefundable;
	
	@Column(name = "booking_time")
	@CreationTimestamp
	private LocalDateTime bookingTime;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private Users user;
	
	@ManyToOne
	@JoinColumn(name = "slot_id")
	private ParkingSlots parkingSlot;

	
	
	public Bookings() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
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

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public double getDueAmount() {
		return dueAmount;
	}

	public void setDueAmount(double dueAmount) {
		this.dueAmount = dueAmount;
	}

	public String getPromocode() {
		return promocode;
	}

	public void setPromocode(String promocode) {
		this.promocode = promocode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public boolean isRefundable() {
		return isRefundable;
	}

	public void setRefundable(boolean isRefundable) {
		this.isRefundable = isRefundable;
	}

	public LocalDateTime getBookingTime() {
		return bookingTime;
	}

	public void setBookingTime(LocalDateTime bookingTime) {
		this.bookingTime = bookingTime;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public ParkingSlots getParkingSlot() {
		return parkingSlot;
	}

	public void setParkingSlot(ParkingSlots parkingSlot) {
		this.parkingSlot = parkingSlot;
	}
	
}
