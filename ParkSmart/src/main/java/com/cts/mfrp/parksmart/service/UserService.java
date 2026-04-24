package com.cts.mfrp.parksmart.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cts.mfrp.parksmart.dto.LoginRequestDTO;
import com.cts.mfrp.parksmart.dto.SignUpRequestDTO;
import com.cts.mfrp.parksmart.dto.UserProfileDTO;
import com.cts.mfrp.parksmart.dto.WalletDTO;
import com.cts.mfrp.parksmart.model.Users;
import com.cts.mfrp.parksmart.model.WalletTransactions;
import com.cts.mfrp.parksmart.repository.UserRepository;
import com.cts.mfrp.parksmart.repository.WalletTransactionsRepository;

@Service
public class UserService {

	
	@Autowired
	private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired 
    private WalletTransactionsRepository walletTransactionsRepository;
    
    @Autowired
    private EmailService emailService;
    
    public String registerUser(SignUpRequestDTO request) {

        String email = request.getEmail().trim().toLowerCase();
        Users existingUser = userRepository.findByEmail(email)
        				.orElse(null);     
        if(existingUser != null)
        {
        	throw new RuntimeException("email already exists");
        }
        String password = request.getPassword().trim();
        String confirmPassword = request.getConfirmPassword();
        
        if(password.equals(confirmPassword) == false)
        {
        	throw new RuntimeException("password and confirm password does not match");
        }

        Users user = new Users();
        user.setUserName(request.getUserName());
        user.setEmail(email);
        user.setPhoneNumber(request.getPhoneNumber().trim());
        user.setPassword(passwordEncoder.encode(password));
        user.setBalance(10000.0);
        
        userRepository.save(user);
        
        return "Account created successfully";
    }


    public Users login(LoginRequestDTO request) {

        String email = request.getEmail().trim().toLowerCase();
        String password = request.getPassword().trim();

        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        return user;
    }


    public UserProfileDTO getUserProfile(String email) {
    	
    	email = email.trim();
    	Users user = userRepository.findByEmail(email)
    					.orElseThrow(
    							() -> new RuntimeException("User not found")
    							);
        UserProfileDTO profile = new UserProfileDTO();
        
        profile.setUserName(user.getUserName());
        profile.setEmail(user.getEmail());
        profile.setPhoneNumber(user.getPhoneNumber());
        profile.setBalance(user.getBalance());
        profile.setUserId(user.getUserId());
        
        return profile;
    }

    public void generateResetToken(String email) {

        Users user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

        user.setResetToken(UUID.randomUUID().toString());
        user.setResetTokenExpiry(LocalDateTime.now().plusMinutes(30));

        userRepository.save(user);

        String resetLink = "https://frontend.app/reset-password?token="
                + user.getResetToken();

        
        emailService.send(
            email,
            "Reset Your Password",
            "Click the link to reset your password:\n" + resetLink
        );
    }
    
    public void validateResetToken(String token) {

        Users user = userRepository.findByResetToken(token)
            .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired");
        }
    }
    
    public void resetPassword(String token, String newPassword) {

        Users user = userRepository.findByResetToken(token)
            .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);

        userRepository.save(user);
    }
    
    
    public WalletDTO getWalletDetails(String email) {
    	
    	email = email.trim();
    	
    	Users user = userRepository.findByEmail(email)
    					.orElseThrow(
    							() -> new RuntimeException("user not found")
    							);
    	
    	WalletTransactions wallet = walletTransactionsRepository.findById(user.getUserId())
    									.orElseThrow(
    											() -> new RuntimeException("wallet not found")
    											);
    	WalletDTO walletDetails = new WalletDTO();
    	walletDetails.setTransactionTime(wallet.getTimestamp());
    	walletDetails.setDescription(wallet.getDescription());
    	walletDetails.setTransactionType(wallet.getTransactionType());
    	walletDetails.setAmount(wallet.getAmount());
    	walletDetails.setTransactionId(wallet.getTransactionId());
    	
    	return walletDetails;
    }
}