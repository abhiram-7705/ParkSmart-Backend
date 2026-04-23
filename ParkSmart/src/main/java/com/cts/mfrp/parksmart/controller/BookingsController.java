package com.cts.mfrp.parksmart.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.mfrp.parksmart.dto.ApplyPromoRequestDTO;
import com.cts.mfrp.parksmart.dto.BookingInitiateRequestDTO;
import com.cts.mfrp.parksmart.dto.BookingResponseDTO;
import com.cts.mfrp.parksmart.dto.BookingSummaryResponseDTO;
import com.cts.mfrp.parksmart.dto.ConfirmBookingRequestDTO;
import com.cts.mfrp.parksmart.dto.ExtendHoldRequestDTO;
import com.cts.mfrp.parksmart.dto.InvoiceRequestDTO;
import com.cts.mfrp.parksmart.dto.InvoiceResponseDTO;
import com.cts.mfrp.parksmart.service.BookingsService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin
public class BookingsController {

    @Autowired
    private BookingsService bookingService;

    @PostMapping("/bookings/initiate")
    public ResponseEntity<BookingSummaryResponseDTO> initiateBooking(
            @Valid @RequestBody BookingInitiateRequestDTO request,
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                bookingService.initiateBooking(request, email)
        );
    }
    @PostMapping("/bookings/apply-promo")
    public ResponseEntity<BookingSummaryResponseDTO> applyPromo(
            @Valid @RequestBody ApplyPromoRequestDTO request,
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                bookingService.applyPromo(request, email)
        );
    }
    
    @PostMapping("/bookings/extend-hold")
    public ResponseEntity<LocalDateTime> extendHold(
            @Valid @RequestBody ExtendHoldRequestDTO request,
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                bookingService.extendHold(request.getHoldId(), email)
        );
    }
    
    @PostMapping("/booking/confirm")
    public ResponseEntity<BookingResponseDTO> confirmBooking(
            @Valid @RequestBody ConfirmBookingRequestDTO request,
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                bookingService.confirmBooking(request, email)
        );
    }
    @PostMapping("/bookings/invoice")
    public ResponseEntity<InvoiceResponseDTO> getInvoice(
            @RequestBody InvoiceRequestDTO request,
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                bookingService.getInvoice(request.getBookingIds(), email)
        );
    }
}
