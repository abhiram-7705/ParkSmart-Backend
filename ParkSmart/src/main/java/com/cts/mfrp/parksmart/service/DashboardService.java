package com.cts.mfrp.parksmart.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.mfrp.parksmart.dto.BookingCardDTO;
import com.cts.mfrp.parksmart.dto.BookingSearchRequestDTO;
import com.cts.mfrp.parksmart.dto.RatingRequestDTO;
import com.cts.mfrp.parksmart.model.Bookings;
import com.cts.mfrp.parksmart.model.ParkingSlots;
import com.cts.mfrp.parksmart.model.ParkingSpaces;
import com.cts.mfrp.parksmart.model.Reviews;
import com.cts.mfrp.parksmart.model.Users;
import com.cts.mfrp.parksmart.model.WalletTransactions;
import com.cts.mfrp.parksmart.repository.BookingsRepository;
import com.cts.mfrp.parksmart.repository.ReviewsRepository;
import com.cts.mfrp.parksmart.repository.UserRepository;
import com.cts.mfrp.parksmart.repository.WalletTransactionsRepository;

import jakarta.transaction.Transactional;

@Service
public class DashboardService {
	
	@Autowired
	private BookingsRepository bookingsRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private WalletTransactionsRepository walletTransactionsRepository;
	@Autowired
	private ReviewsRepository reviewsRepository;
	
	public List<String> getBookingSuggestions(String query, String email) {

	    if (query == null || query.length() < 2) {
	        return Collections.emptyList();
	    }

	    List<String> rawResults =
	            bookingsRepository.findSuggestionsByUserAndQuery(email, query.toLowerCase());

	    return rawResults.stream()
	            .distinct()
	            .limit(10)
	            .collect(Collectors.toList());
	}
	
	public List<BookingCardDTO> searchUserBookings(BookingSearchRequestDTO request, String email) {

	    Users user = userRepository.findByEmail(email)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    List<Bookings> bookings = bookingsRepository.findByUserId(user.getUserId());

	    List<BookingCardDTO> result = bookings.stream()
	            .map(this::mapToDTO)
	            .filter(dto -> filterByQuery(dto, request.getQuery()))
	            .filter(dto -> filterByStatus(dto, request.getStatus()))
	            .filter(dto -> filterByType(dto, request.getTypes()))
	            .collect(Collectors.toList());

	    sortResults(result, request.getSortBy());

	    return result;
	}
	
	private BookingCardDTO mapToDTO(Bookings booking) {

	    ParkingSlots slot = booking.getParkingSlot();
	    ParkingSpaces space = slot.getParkingSpace();

	    BookingCardDTO dto = new BookingCardDTO();

	    dto.setBookingId(booking.getBookingId());
	    dto.setSpaceName(space.getName());
	    dto.setCity(space.getCity());
	    dto.setSpaceType(space.getType());
	    dto.setSlotNumber(slot.getSlotNumber());

	    dto.setArrival(booking.getArrival());
	    dto.setLeaving(booking.getLeaving());

	    dto.setTotalAmount(booking.getTotalAmount());
	    dto.setPricePerHour(space.getPricePerHour());

	    String status = deriveStatus(booking);
	    dto.setStatus(status);
	    
	    dto.setLatitude(space.getLatitude()); 
	    dto.setLongitude(space.getLongitude());

	    // UI FLAGS
	    dto.setIsCancelable(isCancelable(booking));
	    dto.setIsExtendable(isExtendable(booking));
	    dto.setIsContactAllowed(isContactAllowed(status));
	    dto.setIsRatingAllowed(isRatingAllowed(status));
	    dto.setIsReceiptAvailable(true);

	    return dto;
	}
	
	private String deriveStatus(Bookings booking) {

	    if ("CANCELLED".equalsIgnoreCase(booking.getStatus())) {
	        return "CANCELLED";
	    }

	    LocalDateTime now = LocalDateTime.now();

	    if (now.isBefore(booking.getArrival())) {
	        return "UPCOMING";
	    }

	    if (now.isAfter(booking.getLeaving())) {
	        return "PAST";
	    }

	    return "ACTIVE";
	}

	private boolean isCancelable(Bookings booking) {

		LocalDateTime now = LocalDateTime.now();

		return now.isBefore(booking.getArrival()) && booking.getArrival().isAfter(now.plusHours(4));
	}

	private boolean isExtendable(Bookings booking) {
	    return "ACTIVE".equalsIgnoreCase(deriveStatus(booking));
	}

	private boolean isContactAllowed(String status) {
	    return "ACTIVE".equals(status) || "UPCOMING".equals(status);
	}

	private boolean isRatingAllowed(String status) {
	    return "PAST".equals(status);
	}
	private boolean filterByQuery(BookingCardDTO dto, String query) {

	    if (query == null || query.isBlank()) return true;

	    String q = query.toLowerCase();

	    return dto.getSpaceName().toLowerCase().contains(q)
	            || dto.getCity().toLowerCase().contains(q)
	            || dto.getSlotNumber().toLowerCase().contains(q);
	}
	
	private boolean filterByStatus(BookingCardDTO dto, List<String> statuses) {

	    if (statuses == null || statuses.isEmpty()) return true;

	    return statuses.stream()
	            .map(String::toUpperCase)
	            .anyMatch(s -> s.equals(dto.getStatus()));
	}
	
	private boolean filterByType(BookingCardDTO dto, List<String> types) {

	    if (types == null || types.isEmpty()) return true;

	    return types.stream()
	            .map(String::toUpperCase)
	            .anyMatch(t -> t.equals(dto.getSpaceType()));
	}
	
	private void sortResults(List<BookingCardDTO> list, String sortBy) {

	    if (sortBy == null) return;

	    switch (sortBy.toLowerCase()) {

	        case "arrival_asc":
	            list.sort(Comparator.comparing(BookingCardDTO::getArrival));
	            break;

	        case "arrival_desc":
	            list.sort(Comparator.comparing(BookingCardDTO::getArrival).reversed());
	            break;

	        case "price_low":
	            list.sort(Comparator.comparing(BookingCardDTO::getTotalAmount));
	            break;

	        case "price_high":
	            list.sort(Comparator.comparing(BookingCardDTO::getTotalAmount).reversed());
	            break;
	    }
	}
	
	@Transactional
	public void cancelBooking(Integer bookingId, String email) {

	    Bookings booking = bookingsRepository.findById(bookingId)
	            .orElseThrow(() -> new RuntimeException("Booking not found"));

	    if (!booking.getUser().getEmail().equals(email)) {
	        throw new RuntimeException("Unauthorized access");
	    }

	    if ("CANCELLED".equalsIgnoreCase(booking.getStatus())) {
	        throw new RuntimeException("Booking already cancelled");
	    }

	    if (!isUpcoming(booking)) {
	        throw new RuntimeException("Only upcoming bookings can be cancelled");
	    }

	    if (booking.getArrival().isBefore(LocalDateTime.now().plusHours(4))) {
	        throw new RuntimeException("Cancellation allowed only before 4 hours of arrival");
	    }

	    booking.setStatus("CANCELLED");
	    bookingsRepository.save(booking);

	    Users user = booking.getUser();

	    double refundAmount = booking.getTotalAmount();

	    user.setBalance(user.getBalance() + refundAmount);
	    userRepository.save(user);

	    WalletTransactions txn = new WalletTransactions();
	    txn.setUser(user);
	    txn.setAmount(refundAmount);
	    txn.setTransactionType("REFUND");
	    txn.setDescription("Refund for cancelled booking ID " + bookingId);
	    txn.setTimestamp(LocalDateTime.now());

	    walletTransactionsRepository.save(txn);
	}
	
	private boolean isUpcoming(Bookings booking) {
	    return LocalDateTime.now().isBefore(booking.getArrival());
	}
	
	private boolean isPast(Bookings booking) {
	    return LocalDateTime.now().isAfter(booking.getLeaving());
	}
	
	public void submitRating(RatingRequestDTO dto, String email) {

	    Bookings booking = bookingsRepository.findById(dto.getBookingId())
	            .orElseThrow(() -> new RuntimeException("Booking not found"));

	    if (!booking.getUser().getEmail().equals(email)) {
	        throw new RuntimeException("Unauthorized access");
	    }

	    if (!isPast(booking)) {
	        throw new RuntimeException("Rating allowed only for completed bookings");
	    }

	    if (reviewsRepository.existsByBookingId(dto.getBookingId())) {
	        throw new RuntimeException("You have already rated this booking");
	    }


	    Reviews review = new Reviews();

	    review.setRating(dto.getRating());
	    review.setComment(dto.getComment());
	    review.setCreatedAt(LocalDateTime.now());

	    review.setUser(booking.getUser());
	    review.setParkingSpace(booking.getParkingSlot().getParkingSpace());

	    reviewsRepository.save(review);
	}

}
