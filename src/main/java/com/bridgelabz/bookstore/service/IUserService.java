package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.dto.UserDTO;

public interface IUserService {

    String createUserRegistration(UserDTO userDTO);

    String verifyEmail(String token);

}
