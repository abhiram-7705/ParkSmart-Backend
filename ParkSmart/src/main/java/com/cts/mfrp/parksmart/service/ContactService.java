package com.cts.mfrp.parksmart.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.mfrp.parksmart.dto.ContactRequestDTO;
import com.cts.mfrp.parksmart.model.ContactRequests;
import com.cts.mfrp.parksmart.model.ParkingSpaces;
import com.cts.mfrp.parksmart.model.Users;
import com.cts.mfrp.parksmart.repository.ContactRequestsRepository;
import com.cts.mfrp.parksmart.repository.ParkingSpacesRepository;
import com.cts.mfrp.parksmart.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class ContactService {
	
	
	@Autowired
	private ContactRequestsRepository contactRepository;
	@Autowired
    private UserRepository userRepository;
	@Autowired
	private ParkingSpacesRepository parkingSpacesRepository;
	
    @Transactional
    public void saveContactRequest(ContactRequestDTO dto, String email) {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        ParkingSpaces parkingSpace = parkingSpacesRepository.findById(dto.getSpaceId())
                .orElseThrow(() -> new RuntimeException("Parking Space not found"));
        
        ContactRequests contactRequest = new ContactRequests();
        
        contactRequest.setUser(user);
        contactRequest.setParkingSpace(parkingSpace);
        contactRequest.setName(dto.getName());
        contactRequest.setPhone(dto.getPhone());
        contactRequest.setEmail(dto.getEmail());
        contactRequest.setMessage(dto.getMessage());
        contactRequest.setPreferredContact(dto.getPreferredContact());
        contactRequest.setPreferredTime(dto.getPreferredTime());

        contactRepository.save(contactRequest);
    }
}
