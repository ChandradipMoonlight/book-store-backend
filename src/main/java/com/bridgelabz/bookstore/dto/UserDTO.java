package com.bridgelabz.bookstore.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {


    @NotEmpty(message = "First Name cannot be null")
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s]+$", message = "First Name Invalid")
    private String firstName;

    @NotEmpty(message = "Last Name cannot be null")
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s]+$", message = "Last Name Invalid")
    private String lastName;

    @NotEmpty(message = "KYC cannot be null")
    private String kyc;

    @NotNull(message = "Date of Birth should not be Empty")
    @Past(message = "Date of Birth should be past date")
    private LocalDate dataOfBirth;

    @NotEmpty(message = "Phone Number can not be null.")
    @Pattern(regexp = "^[6-9][0-9]{9}$")
    private String phoneNumber;

    @NotNull(message = "Email should not be Empty")
    @Email(message = "Email invalid")
    private String email;

    @NotEmpty(message = "Password cannot be Empty")
    @Pattern(regexp = "^(?=.*[0-9])(?=[^@#$%^&+=]*[@#$%^&+=][^@#$%^&+=]*$)(?=.*[a-z])(?=.*[A-Z]).{8,}$", message = "Password Invalid")
    private String password;

    @Pattern(regexp = "User|Admin", message ="User Type should be Admin or User")
    private String userType;
}
