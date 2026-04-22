package com.cts.mfrp.parksmart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.cts.mfrp.parksmart.dto.UserProfileDTO;
import com.cts.mfrp.parksmart.dto.WalletDTO;
import com.cts.mfrp.parksmart.service.UserService;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserProfileDTO> getProfile(Authentication authentication) {
    	
    	String email = authentication.getName();
        UserProfileDTO profile = userService.getUserProfile(email);
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/wallet")
    public WalletDTO getWallet(Authentication authentication) {
    	String email = authentication.getName();
        WalletDTO wallet = userService.getWalletDetails(email);
        return wallet;
    }
}