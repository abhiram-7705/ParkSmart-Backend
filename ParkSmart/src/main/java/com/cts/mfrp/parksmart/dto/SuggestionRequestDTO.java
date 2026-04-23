package com.cts.mfrp.parksmart.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SuggestionRequestDTO {
	@NotBlank
	@Size(min = 2)
	private String query;

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}
	
	

}
