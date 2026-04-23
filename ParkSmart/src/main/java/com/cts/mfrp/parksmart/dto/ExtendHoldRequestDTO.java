package com.cts.mfrp.parksmart.dto;

import jakarta.validation.constraints.NotBlank;

public class ExtendHoldRequestDTO {

    @NotBlank
    private String holdId;

	public String getHoldId() {
		return holdId;
	}

	public void setHoldId(String holdId) {
		this.holdId = holdId;
	}
    
}
