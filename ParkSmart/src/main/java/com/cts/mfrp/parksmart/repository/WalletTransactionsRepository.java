package com.cts.mfrp.parksmart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.mfrp.parksmart.model.WalletTransactions;

public interface WalletTransactionsRepository extends JpaRepository<WalletTransactions, Integer> {

}
