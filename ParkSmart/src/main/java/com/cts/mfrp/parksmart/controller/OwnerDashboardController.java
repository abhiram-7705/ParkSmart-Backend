package com.cts.mfrp.parksmart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.mfrp.parksmart.dto.OwnerSlotDTO;
import com.cts.mfrp.parksmart.dto.ParkingCardDTO;
import com.cts.mfrp.parksmart.dto.SlotIdRequestDTO;
import com.cts.mfrp.parksmart.dto.SpaceIdRequestDTO;
import com.cts.mfrp.parksmart.service.OwnerDashboardService;

@RestController
@RequestMapping("/api/owner-dashboard")
@CrossOrigin
public class OwnerDashboardController {
	
	@Autowired
	private OwnerDashboardService ownerDashboardService;

    @PostMapping("/dashboard/cards")
    public ResponseEntity<List<ParkingCardDTO>> getOwnerParkingCards(
            Authentication authentication) {

        String email = authentication.getName();

        List<ParkingCardDTO> cards =
                ownerDashboardService.getParkingCardsForOwner(email);

        return ResponseEntity.ok(cards);
    }
    

    @PostMapping("/slots")
    public ResponseEntity<List<OwnerSlotDTO>> getSlotsForSpace(
            @RequestBody SpaceIdRequestDTO request,
            Authentication authentication) {

        String email = authentication.getName();

        List<OwnerSlotDTO> slots =
                ownerDashboardService.getSlotsForOwnerSpace(email, request.getSpaceId());

        return ResponseEntity.ok(slots);
    }
    

    @PostMapping("/slots/toggle-status")
    public ResponseEntity<Void> toggleSlotStatus(
            @RequestBody SlotIdRequestDTO request,
            Authentication authentication) {

        ownerDashboardService.toggleSlotStatus(
                authentication.getName(),
                request.getSlotId()
        );

        return ResponseEntity.ok().build();
    }



}
