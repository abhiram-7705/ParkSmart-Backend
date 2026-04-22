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
public class ContactRequests {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "request_id")
	private int requestId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "phone")
	private long phone;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "preferred_contact")
	private String preferredContact;
	
	@Column(name = "message")
	private String message;
	
	@Column(name = "preferred_time")
	private String preferredTime;
	
	@Column(name = "requested_at")
	@CreationTimestamp
	private LocalDateTime requestedAt;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private Users user;
	
	@ManyToOne
	@JoinColumn(name = "space_id")
	private ParkingSpaces parkingSpace;
	
	

	public ContactRequests() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPreferredContact() {
		return preferredContact;
	}

	public void setPreferredContact(String preferredContact) {
		this.preferredContact = preferredContact;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPreferredTime() {
		return preferredTime;
	}

	public void setPreferredTime(String preferredTime) {
		this.preferredTime = preferredTime;
	}

	public LocalDateTime getRequestedAt() {
		return requestedAt;
	}

	public void setRequestedAt(LocalDateTime requestedAt) {
		this.requestedAt = requestedAt;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public ParkingSpaces getParkingSpace() {
		return parkingSpace;
	}

	public void setParkingSpace(ParkingSpaces parkingSpace) {
		this.parkingSpace = parkingSpace;
	}
	
	
	
	
}
