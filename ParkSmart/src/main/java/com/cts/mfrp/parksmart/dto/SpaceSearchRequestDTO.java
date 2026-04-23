package com.cts.mfrp.parksmart.dto;
import java.util.List;

import jakarta.validation.constraints.*;

public class SpaceSearchRequestDTO {

    private String location;

    private String arrival;
    private String leaving;

    private List<String> types;

    @PositiveOrZero
    private Double minPrice;

    @Positive
    private Double maxPrice;

    private List<String> amenities;

    @Positive
    private Double radius;

    @Pattern(regexp = "distance|price_low|price_high|rating", message = "Invalid sort")
    private String sortBy;

    @Min(1)
    private int page = 1;

    @Min(1)
    @Max(50)
    private int limit = 10;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getArrival() {
		return arrival;
	}

	public void setArrival(String arrival) {
		this.arrival = arrival;
	}

	public String getLeaving() {
		return leaving;
	}

	public void setLeaving(String leaving) {
		this.leaving = leaving;
	}

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public Double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Double minPrice) {
		this.minPrice = minPrice;
	}

	public Double getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(Double maxPrice) {
		this.maxPrice = maxPrice;
	}

	public List<String> getAmenities() {
		return amenities;
	}

	public void setAmenities(List<String> amenities) {
		this.amenities = amenities;
	}

	public Double getRadius() {
		return radius;
	}

	public void setRadius(Double radius) {
		this.radius = radius;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	   
}