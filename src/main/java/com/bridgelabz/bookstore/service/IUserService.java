package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.dto.UserDTO;
import com.bridgelabz.bookstore.dto.UserLoginDTO;

public interface IUserService {

    String createUserRegistration(UserDTO userDTO);

    String verifyEmail(String token);

    String loginUser(UserLoginDTO userLoginDTO);

    String forgetPassword(String email);
}
