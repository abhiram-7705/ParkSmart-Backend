package com.cts.mfrp.parksmart.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "role")
    private String role;

    @Column(name = "wallet_balance")
    private double balance;


	@Column
	private String resetToken;
	
	@Column
	private LocalDateTime resetTokenExpiry;

    
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<WalletTransactions> transactions;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Bookings> bookings;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Reviews> reviews;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<ContactRequests> contactRequests;

    @JsonIgnore
    @OneToMany(mappedBy = "owner")
    private List<ParkingSpaces> parkingSpaces;

    public Users() {
        super();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<WalletTransactions> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<WalletTransactions> transactions) {
        this.transactions = transactions;
    }

    public List<Bookings> getBookings() {
        return bookings;
    }

    public void setBookings(List<Bookings> bookings) {
        this.bookings = bookings;
    }

    public List<Reviews> getReviews() {
        return reviews;
    }

    public void setReviews(List<Reviews> reviews) {
        this.reviews = reviews;
    }

    public List<ContactRequests> getContactRequests() {
        return contactRequests;
    }

    public void setContactRequests(List<ContactRequests> contactRequests) {
        this.contactRequests = contactRequests;
    }

    public List<ParkingSpaces> getParkingSpaces() {
        return parkingSpaces;
    }

    public void setParkingSpaces(List<ParkingSpaces> parkingSpaces) {
        this.parkingSpaces = parkingSpaces;
    }

	public String getResetToken() {
		return resetToken;
	}

	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}

	public LocalDateTime getResetTokenExpiry() {
		return resetTokenExpiry;
	}

	public void setResetTokenExpiry(LocalDateTime resetTokenExpiry) {
		this.resetTokenExpiry = resetTokenExpiry;
	}
    
    
}
