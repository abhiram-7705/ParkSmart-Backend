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
public class ParkingSpaces {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "space_id")
	private int spaceId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "location")
	private String location;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "latitude")
	private double latitude;
	
	@Column(name = "longitude")
	private double longitude;
	
	@Column(name = "price_per_hour")
	private double pricePerHour;
	
	@Column(name = "ev_charging")
	private boolean evCharging;
	
	@Column(name = "covered_fence")
	private boolean coveredFence;
	
	@Column(name = "cctv")
	private boolean cctv;
	
	@Column(name = "guarded")
	private boolean guarded;
	
	@Column(name = "notes")
	private String notes;
	
	@Column(name = "is_active")
	private boolean isActive;
	
	@OneToMany(mappedBy = "parkingSpace")
	private List<ParkingSlots> parkingSlots;
	
	@OneToMany(mappedBy = "parkingSpace")
	private List<ContactRequests> requests;
	
	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Users owner;
	
	@OneToMany(mappedBy = "parkingSpace")
	private List<Reviews> reviews;
	
	

	public ParkingSpaces() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getSpaceId() {
		return spaceId;
	}

	public void setSpaceId(int spaceId) {
		this.spaceId = spaceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getPricePerHour() {
		return pricePerHour;
	}

	public void setPricePerHour(double pricePerHour) {
		this.pricePerHour = pricePerHour;
	}

	public boolean isEvCharging() {
		return evCharging;
	}

	public void setEvCharging(boolean evCharging) {
		this.evCharging = evCharging;
	}

	public boolean isCoveredFence() {
		return coveredFence;
	}

	public void setCoveredFence(boolean coveredFence) {
		this.coveredFence = coveredFence;
	}

	public boolean isCctv() {
		return cctv;
	}

	public void setCctv(boolean cctv) {
		this.cctv = cctv;
	}

	public boolean isGuarded() {
		return guarded;
	}

	public void setGuarded(boolean guarded) {
		this.guarded = guarded;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public List<ParkingSlots> getParkingSlots() {
		return parkingSlots;
	}

	public void setParkingSlots(List<ParkingSlots> parkingSlots) {
		this.parkingSlots = parkingSlots;
	}

	public List<ContactRequests> getRequests() {
		return requests;
	}

	public void setRequests(List<ContactRequests> requests) {
		this.requests = requests;
	}

	public Users getOwner() {
		return owner;
	}

	public void setOwner(Users owner) {
		this.owner = owner;
	}

	public List<Reviews> getReviews() {
		return reviews;
	}

	public void setReviews(List<Reviews> reviews) {
		this.reviews = reviews;
	}
	
	
}
