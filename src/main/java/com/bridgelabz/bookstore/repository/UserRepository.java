package com.bridgelabz.bookstore.repository;

import com.bridgelabz.bookstore.model.UserRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserRegistration, Integer> {

    Optional<UserRegistration> findByEmail(String email);
}
