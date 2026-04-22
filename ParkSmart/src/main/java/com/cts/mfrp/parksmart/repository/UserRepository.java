package com.cts.mfrp.parksmart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.mfrp.parksmart.model.Users;

public interface UserRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findByEmail(String email);

	Optional<Users> findByResetToken(String token);
}
