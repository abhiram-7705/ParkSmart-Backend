package com.cts.mfrp.parksmart.dto;

import java.time.LocalDateTime;

public class SlotHoldResponseDTO {
	
    private String holdId;
    
    private LocalDateTime expiresAt;

    public SlotHoldResponseDTO(String holdId, LocalDateTime expiresAt) {
        this.holdId = holdId;
        this.expiresAt = expiresAt;
    }

	public String getHoldId() {
		return holdId;
	}

	public void setHoldId(String holdId) {
		this.holdId = holdId;
	}

	public LocalDateTime getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(LocalDateTime expiresAt) {
		this.expiresAt = expiresAt;
	}
    
    
    
}