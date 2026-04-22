package com.cts.mfrp.parksmart.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import com.cts.mfrp.parksmart.dto.*;
import com.cts.mfrp.parksmart.model.Users;
import com.cts.mfrp.parksmart.service.UserService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(
            @Valid @RequestBody SignUpRequestDTO request) {
        userService.registerUser(request);
        return ResponseEntity.ok("user signup suceesful");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Valid @RequestBody LoginRequestDTO request, HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        Users user = userService.login(request);

		Authentication authentication =
		        new UsernamePasswordAuthenticationToken(
		            user.getEmail(),
		            null,
		            Collections.emptyList()
		        );

		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(authentication);
		
		HttpSession session = httpRequest.getSession(true);
		    session.setAttribute(
		        HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
		        context
		    );


		if (request.isRememberMe()) {
		        Cookie cookie = new Cookie("remember-me", user.getEmail());
		        cookie.setHttpOnly(true);
		        cookie.setMaxAge(7 * 24 * 60 * 60);
		        cookie.setPath("/");
		        httpResponse.addCookie(cookie);
		    }

        return ResponseEntity.ok("user login succesful");
    }

	@PostMapping("/logout")
	public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
	    request.getSession().invalidate();   
	    SecurityContextHolder.clearContext();
		Cookie cookie = new Cookie("remember-me", null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
	    return ResponseEntity.ok().build();
	}
	
	@PostMapping("/forgot-password")
	public ResponseEntity<String> forgotPassword(
	        @RequestBody Map<String, String> body) {

	    String email = body.get("email");

	    if (email == null || email.isBlank()) {
	        throw new IllegalArgumentException("Email is required");
	    }

	    userService.generateResetToken(email);
	    return ResponseEntity.ok("Password reset link sent to email");
	}

	
	@PostMapping("/reset-password/validate")
	public ResponseEntity<String> validateResetToken(
	        @RequestBody Map<String, String> body) {

	    String token = body.get("token");

	    if (token == null || token.isBlank()) {
	        throw new IllegalArgumentException("Token is required");
	    }

	    userService.validateResetToken(token);
	    return ResponseEntity.ok("Token is valid");
	}
	
	@PostMapping("/reset-password")
	public ResponseEntity<String> resetPassword(
	        @RequestBody Map<String, String> body) {

	    String token = body.get("token");
	    String password = body.get("password");
	    String confirmPassword = body.get("confirmPassword");

	    if (token == null || password == null || confirmPassword == null) {
	        throw new IllegalArgumentException("All fields are required");
	    }

	    if (!password.equals(confirmPassword)) {
	        throw new IllegalArgumentException("Passwords do not match");
	    }

	    userService.resetPassword(token, password);
	    return ResponseEntity.ok("Password reset successful");
	}
}