package com.cts.mfrp.parksmart.dto;

import jakarta.validation.constraints.*;

public class SignUpRequestDTO {

    @NotBlank(message = "User name is required")
    @Size(min = 3, message = "User name must be at least 3 characters")
    private String userName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @NotBlank(message = "Confirm Password is required")
    @Size(min = 8, message = "Confirm Password must be at least 8 characters")
    private String confirmPassword;

    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^[1-9][0-9]{9}$",
            message = "Phone number must be a valid 10-digit mobile number"
    )
    private String phoneNumber;
    // Getters & Setters
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }
    
}
