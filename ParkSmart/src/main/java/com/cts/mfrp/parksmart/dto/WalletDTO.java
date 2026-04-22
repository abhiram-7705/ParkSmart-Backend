package com.cts.mfrp.parksmart.dto;

import java.time.LocalDateTime;

public class WalletDTO {
	
	private LocalDateTime transactionTime;
	private String description;
	private String transactionType;
	private double amount;
	private double balance;
	
	public LocalDateTime getTransactionTime() {
		return transactionTime;
	}
	public void setTransactionTime(LocalDateTime transactionTime) {
		this.transactionTime = transactionTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}	

}
