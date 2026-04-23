package com.cts.mfrp.parksmart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.mfrp.parksmart.model.PromoCode;

@Repository
public interface PromoCodeRepository extends JpaRepository<PromoCode, Integer> {
	
	Optional<PromoCode> findByCode(String code);

}
