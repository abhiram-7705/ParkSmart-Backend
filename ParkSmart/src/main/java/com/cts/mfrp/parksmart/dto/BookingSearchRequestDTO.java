package com.cts.mfrp.parksmart.dto;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import java.util.List;

public class BookingSearchRequestDTO {

    @Size(min = 2, max = 50, message = "Query must be between 2 and 50 characters")
    private String query;

    @Size(max = 4, message = "Maximum 4 status filters allowed")
    private List<
            @Pattern(
                regexp = "ACTIVE|UPCOMING|PAST|CANCELLED",
                message = "Invalid status value"
            )
            String> status;

    @Size(max = 2, message = "Maximum 2 types allowed")
    private List<
            @Pattern(
                regexp = "PUBLIC|PRIVATE",
                message = "Invalid type value"
            )
            String> types;

    @Pattern(
        regexp = "arrival_asc|arrival_desc|price_low|price_high",
        message = "Invalid sort value"
    )
    private String sortBy;

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public List<String> getStatus() {
		return status;
	}

	public void setStatus(List<String> status) {
		this.status = status;
	}

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

    
}