package com.bridgelabz.bookstore.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {

    @NotNull(message = "Email should not be Empty")
    @Email(message = "Email invalid")
    private String email;

    @NotEmpty(message = "Password cannot be null")
    @Pattern(regexp = "^(?=.*[0-9])(?=[^@#$%^&+=]*[@#$%^&+=][^@#$%^&+=]*$)(?=.*[a-z])(?=.*[A-Z]).{8,}$", message = "Password Invalid")
    private String password;
}
