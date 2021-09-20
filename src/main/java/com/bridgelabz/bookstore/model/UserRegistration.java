package com.bridgelabz.bookstore.model;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@Entity
public class UserRegistration extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String firstName;
    private String lastName;
    private String kyc;
    private LocalDate dataOfBirth;
    private String phoneNumber;
    private String email;
    private String password;
    private boolean verify;
    private int otp;
    private String userType;
}
