package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.dto.UserDTO;
import com.bridgelabz.bookstore.dto.UserLoginDTO;

import javax.mail.MessagingException;

public interface IUserService {

    String createUserRegistration(UserDTO userDTO);

    String verifyEmailByToken(String token);

    String loginUser(UserLoginDTO userLoginDTO);

    String forgetPassword(String email);

    String resetPassword(String token, String password);

    String purchaseSubscription(String token) throws MessagingException;

}
