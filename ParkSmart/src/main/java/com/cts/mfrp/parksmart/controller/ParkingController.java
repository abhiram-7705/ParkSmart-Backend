package com.cts.mfrp.parksmart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.mfrp.parksmart.dto.SlotAvailabilityRequestDTO;
import com.cts.mfrp.parksmart.dto.SlotAvailabilityResponseDTO;
import com.cts.mfrp.parksmart.dto.SlotHoldRequestDTO;
import com.cts.mfrp.parksmart.dto.SlotHoldResponseDTO;
import com.cts.mfrp.parksmart.dto.SlotReleaseRequestDTO;
import com.cts.mfrp.parksmart.service.ParkingService;


import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/parking")
@CrossOrigin
public class ParkingController {

    @Autowired
    private ParkingService parkingService;

    @PostMapping("/slots/availability")
    public ResponseEntity<SlotAvailabilityResponseDTO> getSlotAvailability(
            @Valid @RequestBody SlotAvailabilityRequestDTO request) {

        return ResponseEntity.ok(parkingService.getSlotAvailability(request));
    }

    @PostMapping("/slots/hold")
    public ResponseEntity<SlotHoldResponseDTO> holdSlots(
            @Valid @RequestBody SlotHoldRequestDTO request,
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(parkingService.holdSlots(request, email));
    }

    @PostMapping("/slots/release")
    public ResponseEntity<String> releaseSlots(
            @Valid @RequestBody SlotReleaseRequestDTO request,
            Authentication authentication) {

        String email = authentication.getName();

        parkingService.releaseSlots(request.getHoldId(), email);

        return ResponseEntity.ok("Slots released");
    }
}