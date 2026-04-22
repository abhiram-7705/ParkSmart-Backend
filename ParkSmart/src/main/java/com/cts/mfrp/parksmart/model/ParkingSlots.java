package com.cts.mfrp.parksmart.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class ParkingSlots {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "slot_id")
	private int slotId;
	
	@Column(name = "slot_number")
	private String slotNumber;
	
	@Column(name = "status")
	private String status;
	
	@OneToMany(mappedBy = "parkingSlot")
	private List<Bookings> bookings;
	
	@ManyToOne
	@JoinColumn(name = "space_id")
	private ParkingSpaces parkingSpace;
	
	

	public ParkingSlots() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getSlotId() {
		return slotId;
	}

	public void setSlotId(int slotId) {
		this.slotId = slotId;
	}

	public String getSlotNumber() {
		return slotNumber;
	}

	public void setSlotNumber(String slotNumber) {
		this.slotNumber = slotNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Bookings> getBookings() {
		return bookings;
	}

	public void setBookings(List<Bookings> bookings) {
		this.bookings = bookings;
	}

	public ParkingSpaces getParkingSpace() {
		return parkingSpace;
	}

	public void setParkingSpace(ParkingSpaces parkingSpace) {
		this.parkingSpace = parkingSpace;
	}
}
