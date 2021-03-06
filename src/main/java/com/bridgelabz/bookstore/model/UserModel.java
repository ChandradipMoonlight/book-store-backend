package com.bridgelabz.bookstore.model;


import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@Entity
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userId;
    private String firstName;
    private String lastName;
    private String kyc;
    private LocalDate dob;
    private String phoneNumber;
    private String email;
    private String password;
    private boolean isVerified;
    private int otp;
    private LocalDate purchaseDate;
    private LocalDate expiryDate;

    @CreationTimestamp
    private LocalDate registeredDate;

    @UpdateTimestamp
    private LocalDate updatedDate;
}
