package com.cts.mfrp.parksmart.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class WalletTransactions {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "transaction_id")
	private int transactionId;
		
	@Column(name = "transaction_type")
	private String transactionType;
	
	@Column(name = "amount")
	private double amount;
	
	@Column(name = "updated_balance")
	private double updatedBalance;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "timestamp")
	@CreationTimestamp
	private LocalDateTime timestamp;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private Users user;
	
	

	public WalletTransactions() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	
	

	public double getUpdatedBalance() {
		return updatedBalance;
	}

	public void setUpdatedBalance(double updatedBalance) {
		this.updatedBalance = updatedBalance;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}
	
	

}
