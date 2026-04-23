package com.cts.mfrp.parksmart.service;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.cts.mfrp.parksmart.dto.ApplyPromoRequestDTO;
import com.cts.mfrp.parksmart.dto.BookingContext;
import com.cts.mfrp.parksmart.dto.BookingInitiateRequestDTO;
import com.cts.mfrp.parksmart.dto.BookingResponseDTO;
import com.cts.mfrp.parksmart.dto.BookingSummaryResponseDTO;
import com.cts.mfrp.parksmart.dto.ConfirmBookingRequestDTO;
import com.cts.mfrp.parksmart.dto.InvoiceResponseDTO;
import com.cts.mfrp.parksmart.dto.PricingResult;
import com.cts.mfrp.parksmart.model.Bookings;
import com.cts.mfrp.parksmart.model.ParkingSlots;
import com.cts.mfrp.parksmart.model.ParkingSpaces;
import com.cts.mfrp.parksmart.model.PromoCode;
import com.cts.mfrp.parksmart.model.SlotHold;
import com.cts.mfrp.parksmart.model.Users;
import com.cts.mfrp.parksmart.model.WalletTransactions;
import com.cts.mfrp.parksmart.repository.BookingsRepository;
import com.cts.mfrp.parksmart.repository.ParkingSpacesRepository;
import com.cts.mfrp.parksmart.repository.PromoCodeRepository;
import com.cts.mfrp.parksmart.repository.SlotHoldRepository;
import com.cts.mfrp.parksmart.repository.UserRepository;
import com.cts.mfrp.parksmart.repository.WalletTransactionsRepository;

import jakarta.transaction.Transactional;


public class BookingsService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private SlotHoldRepository slotHoldRepository;
	@Autowired
	private ParkingSpacesRepository parkingSpacesRepository;
	@Autowired
	private PromoCodeRepository promoCodeRepository;
	@Autowired
	private WalletTransactionsRepository walletTransactionsRepository;
	@Autowired
	private BookingsRepository bookingsRepository;

	private BookingContext buildBookingContext(String holdId, Integer spaceId, LocalDateTime arrival,
			LocalDateTime leaving, Users user) {

		LocalDateTime now = LocalDateTime.now();
		
		List<SlotHold> holds = slotHoldRepository.findByHoldGroupId(holdId);

		if (holds.isEmpty()) {
			throw new RuntimeException("Invalid hold reference");
		}

		for (SlotHold hold : holds) {
			if (hold.getUser().getUserId()!=user.getUserId()) {
				throw new RuntimeException("Unauthorized access to hold");
			}
		}
		for (SlotHold hold : holds) {
			if (hold.getExpiresAt().isBefore(now)) {
				throw new RuntimeException("Hold expired. Please reselect slots");
			}
		}

		for (SlotHold hold : holds) {
			if (!hold.getArrival().equals(arrival) || !hold.getLeaving().equals(leaving)) {
				throw new RuntimeException("Hold timing mismatch");
			}
		}

		List<Integer> slotIds = holds.stream().map(h -> h.getSlot().getSlotId()).toList();

		ParkingSpaces space = parkingSpacesRepository.findById(spaceId)
				.orElseThrow(() -> new RuntimeException("Space not found"));

		for (SlotHold hold : holds) {
			if (hold.getSlot().getParkingSpace().getSpaceId() != space.getSpaceId()) {
				throw new RuntimeException("Slot-space mismatch");
			}
		}

		if (arrival.isAfter(leaving)) {
			throw new RuntimeException("Invalid time range");
		}

		if (arrival.isBefore(now)) {
			throw new RuntimeException("Arrival time cannot be in the past");
		}

		long minutes = Duration.between(arrival, leaving).toMinutes();
		int hours = (int) Math.ceil(minutes / 60.0);

		if (hours <= 0) {
			throw new RuntimeException("Invalid duration");
		}

		BookingContext context = new BookingContext();
		context.setHolds(holds);
		context.setSlotIds(slotIds);
		context.setSpace(space);
		context.setHours(hours);
		context.setBaseRate(space.getPricePerHour());

		return context;

	}
	
	public BookingSummaryResponseDTO initiateBooking(BookingInitiateRequestDTO request, String email) {

		Users user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

		BookingContext ctx = buildBookingContext(request.getHoldId(), request.getSpaceId(), request.getArrival(),
				request.getLeaving(), user);

		PricingResult pricing = calculatePrice(ctx.getBaseRate(), ctx.getHours(), ctx.getSlotIds().size(), null);

		double walletBalance = user.getBalance();
		boolean sufficientBalance = walletBalance >= pricing.getTotal();

		LocalDateTime expiry = ctx.getHolds().stream().map(SlotHold::getExpiresAt).min(LocalDateTime::compareTo)
				.orElse(LocalDateTime.now());

		BookingSummaryResponseDTO response = new BookingSummaryResponseDTO();

		response.setSpaceId(request.getSpaceId());
		response.setSlotIds(ctx.getSlotIds());
		response.setArrival(request.getArrival());
		response.setLeaving(request.getLeaving());

		response.setBaseRate(ctx.getBaseRate());
		response.setDurationHours(ctx.getHours());

		response.setSubtotal(pricing.getSubtotal());
		response.setTax(pricing.getTax());
		response.setTotal(pricing.getTotal());

		response.setWalletBalance(walletBalance);
		response.setSufficientBalance(sufficientBalance);

		response.setHoldExpiry(expiry);

		return response;
	}
	
	private PricingResult calculatePrice(double baseRate, int hours, int slotCount, String promoCode) {

		double subtotal = baseRate * hours * slotCount;
		double discount = 0;

		// 🔥 Promo logic (extend later)
		if (promoCode != null && !promoCode.isBlank()) {
			if (promoCode.equalsIgnoreCase("SAVE10")) {
				discount = subtotal * 0.10;
			}
		}

		double discountedAmount = subtotal - discount;

		double tax = discountedAmount * 0.18;

		double total = discountedAmount + tax;

		PricingResult result = new PricingResult();
		result.setSubtotal(subtotal);
		result.setDiscount(discount);
		result.setTax(tax);
		result.setTotal(total);

		return result;
	}
	
	public BookingSummaryResponseDTO applyPromo(ApplyPromoRequestDTO request, String email) {

	    Users user = userRepository.findByEmail(email)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    BookingContext ctx = buildBookingContext(
	            request.getHoldId(),
	            request.getSpaceId(),
	            request.getArrival(),
	            request.getLeaving(),
	            user
	    );

	    PromoCode promo = promoCodeRepository.findByCode(request.getPromoCode())
	            .orElseThrow(() -> new RuntimeException("Invalid promo code"));

	    if (!promo.isActive()) {
	        throw new RuntimeException("Promo code inactive");
	    }

	    if (promo.getExpiryDate() != null &&
	        promo.getExpiryDate().isBefore(LocalDateTime.now())) {
	        throw new RuntimeException("Promo code expired");
	    }

	    PricingResult pricing = calculatePrice(
	            ctx.getBaseRate(),
	            ctx.getHours(),
	            ctx.getSlotIds().size(),
	            request.getPromoCode()
	    );

	    double walletBalance = user.getBalance();
	    boolean sufficientBalance = walletBalance >= pricing.getTotal();

	    LocalDateTime expiry = ctx.getHolds().stream()
	            .map(SlotHold::getExpiresAt)
	            .min(LocalDateTime::compareTo)
	            .orElse(LocalDateTime.now());

	    BookingSummaryResponseDTO response = new BookingSummaryResponseDTO();

	    response.setSpaceId(request.getSpaceId());
	    response.setSlotIds(ctx.getSlotIds());
	    response.setArrival(request.getArrival());
	    response.setLeaving(request.getLeaving());

	    response.setBaseRate(ctx.getBaseRate());
	    response.setDurationHours(ctx.getHours());

	    response.setSubtotal(pricing.getSubtotal());
	    response.setTax(pricing.getTax());
	    response.setTotal(pricing.getTotal());

	    response.setWalletBalance(walletBalance);
	    response.setSufficientBalance(sufficientBalance);

	    response.setHoldExpiry(expiry);

	    return response;
	}
	
	@Transactional
	public LocalDateTime extendHold(String holdId, String email) {

	    Users user = userRepository.findByEmail(email)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    List<SlotHold> holds = slotHoldRepository.findByHoldGroupId(holdId);

	    if (holds.isEmpty()) {
	        throw new RuntimeException("Invalid hold reference");
	    }

	    LocalDateTime now = LocalDateTime.now();

	    for (SlotHold hold : holds) {

	        if (hold.getUser().getUserId()!=user.getUserId()) {
	            throw new RuntimeException("Unauthorized access to hold");
	        }

	        if (hold.getExpiresAt().isBefore(now)) {
	            throw new RuntimeException("Hold already expired");
	        }

	        if (hold.isExtended()) {
	            throw new RuntimeException("Hold already extended once");
	        }
	    }

	    LocalDateTime newExpiry = holds.get(0).getExpiresAt().plusMinutes(15);

	    for (SlotHold hold : holds) {
	        hold.setExpiresAt(newExpiry);
	        hold.setExtended(true);
	    }

	    return newExpiry;
	}
	
	@Transactional
	public BookingResponseDTO confirmBooking(
	        ConfirmBookingRequestDTO request,
	        String email) {

	    LocalDateTime now = LocalDateTime.now();

	    Users user = userRepository.findByEmail(email)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    BookingContext ctx = buildBookingContext(
	            request.getHoldId(),
	            request.getSpaceId(),
	            request.getArrival(),
	            request.getLeaving(),
	            user
	    );

	    PricingResult pricing = calculatePrice(
	            ctx.getBaseRate(),
	            ctx.getHours(),
	            ctx.getSlotIds().size(),
	            request.getPromoCode()
	    );

	    if (user.getBalance() < pricing.getTotal()) {
	        throw new RuntimeException("Insufficient wallet balance");
	    }

	    WalletTransactions txn = new WalletTransactions();
	    txn.setAmount(pricing.getTotal());
	    txn.setDescription("Parking booking payment");
	    txn.setTimestamp(now);
	    txn.setTransactionType("DEBIT");
	    txn.setUser(user);

	    walletTransactionsRepository.save(txn);

	    user.setBalance(user.getBalance() - pricing.getTotal());

	    List<Integer> bookingIds = new ArrayList<>();

	    for (SlotHold hold : ctx.getHolds()) {

	        ParkingSlots slot = hold.getSlot();

	        boolean alreadyBooked = bookingsRepository
	                .existsBySlotIdAndArrivalLessThanAndLeavingGreaterThan(
	                        slot.getSlotId(),
	                        request.getLeaving(),
	                        request.getArrival()
	                );

	        if (alreadyBooked) {
	            throw new RuntimeException("Slot already booked");
	        }

	        Bookings booking = new Bookings();

	        booking.setArrival(request.getArrival());
	        booking.setLeaving(request.getLeaving());
	        booking.setBookingTime(now);
	        
	        double perSlotTotal = pricing.getTotal() / ctx.getSlotIds().size();

	        booking.setBaseRate(ctx.getBaseRate());
	        booking.setSubtotal(pricing.getSubtotal());
	        booking.setTax(pricing.getTax());
	        booking.setTotalAmount(perSlotTotal);

	        booking.setDueAmount(0);
	        booking.setRefundable(true);

	        booking.setPromocode(request.getPromoCode());
	        booking.setVehicleNumber(request.getVehicleNumber());

	        booking.setStatus("CONFIRMED");
	        booking.setPaymentStatus("SUCCESS");

	        booking.setUser(user);
	        booking.setParkingSlot(slot);

	        bookingsRepository.save(booking);

	        bookingIds.add(booking.getBookingId());
	    }

	    slotHoldRepository.deleteByHoldGroupIdAndUser_Email(
	            request.getHoldId(),
	            email
	    );

	    BookingResponseDTO response = new BookingResponseDTO();
	    response.setBookingIds(bookingIds);
	    response.setStatus("CONFIRMED");

	    return response;
	}
	
	public InvoiceResponseDTO getInvoice(List<Integer> bookingIds, String email) {

	    if (bookingIds == null || bookingIds.isEmpty()) {
	        throw new RuntimeException("Invalid booking request");
	    }

	    Users user = userRepository.findByEmail(email)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    List<Bookings> bookings = bookingsRepository.findAllById(bookingIds);

	    if (bookings.isEmpty()) {
	        throw new RuntimeException("Bookings not found");
	    }

	    for (Bookings b : bookings) {
	        if (b.getUser().getUserId()!=user.getUserId()) {
	            throw new RuntimeException("Unauthorized access");
	        }
	    }

	    Bookings primary = bookings.get(0);

	    List<String> slotNumbers = bookings.stream()
	            .map(b -> b.getParkingSlot().getSlotNumber())
	            .toList();

	    ParkingSpaces space = primary.getParkingSlot().getParkingSpace();

	    long minutes = Duration.between(primary.getArrival(), primary.getLeaving()).toMinutes();
	    int hours = (int) Math.ceil(minutes / 60.0);

	    double subtotal = bookings.stream().mapToDouble(Bookings::getSubtotal).sum();
	    double tax = bookings.stream().mapToDouble(Bookings::getTax).sum();
	    double total = bookings.stream().mapToDouble(Bookings::getTotalAmount).sum();

	    double baseRate = primary.getBaseRate();
	    double discount = (subtotal + tax) - total;

	    InvoiceResponseDTO response = new InvoiceResponseDTO();

	    response.setSpaceName(space.getName());
	    response.setLocation(space.getLocation());

	    response.setSlotNumbers(slotNumbers);

	    response.setArrival(primary.getArrival());
	    response.setLeaving(primary.getLeaving());
	    response.setDurationHours(hours);

	    response.setVehicleNumber(primary.getVehicleNumber());

	    response.setBaseRate(baseRate);
	    response.setSubtotal(subtotal);
	    response.setTax(tax);
	    response.setDiscount(discount);
	    response.setTotalAmount(total);

	    response.setStatus(primary.getStatus());

	    return response;
	}

}
