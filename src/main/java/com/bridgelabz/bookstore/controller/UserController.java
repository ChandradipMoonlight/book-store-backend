package com.bridgelabz.bookstore.controller;


import com.bridgelabz.bookstore.builder.MessageProperties;
import com.bridgelabz.bookstore.dto.ResponseDTO;
import com.bridgelabz.bookstore.dto.UserDTO;
import com.bridgelabz.bookstore.dto.UserLoginDTO;
import com.bridgelabz.bookstore.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
public class UserController {


    @Autowired
    private IUserService userService;

    /**
     * Purpose : Ability to insert user details in User Registration repository after sending the verification mail.
     *
     * @param userDTO Object of UserRegistrationDTO which will validate user-input
     *                and once valid, will pass it to the UserRegistration entity.
     *                Finally, the user-input details gets stored in the Database.
     *
     * @return String Object to print the message.
     */

    @PostMapping("/register")
    public ResponseEntity<String> createUserRegistration(@RequestBody @Valid UserDTO userDTO) {
        return new ResponseEntity<>(userService.createUserRegistration(userDTO), HttpStatus.CREATED);
    }

    /**
     * Purpose : Ability to verify email after registration.
     *
     * @param token Object received from the get url.
     *              The token is further matched with the user email,
     *              and the verified column is set as true or false.
     * @return String Object to print the message.
     */

    @GetMapping("verifyEmail")
    public ResponseEntity<String> verifyEmail(@RequestParam(name = "token") String token) {
        log.info("Inside verifyEmail controller method.");
        return new ResponseEntity<>(userService.verifyEmail(token), HttpStatus.OK);
    }

    /**
     * Purpose : Ability to login user after validating details.
     *
     * @param userLoginDTO Object accepts email and password from user.
     *                     If matches user logs in successfully.
     * @return response Object of ResponseDTO which returns the status of the POST Method.
     */

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> userLogin(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        log.info("Inside userLogin controller method.");
        ResponseDTO responseDTO = new ResponseDTO(MessageProperties.LOGIN_SUCCESSFUL.getMessage(), userService.loginUser(userLoginDTO));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    /**
     * Purpose : Ability to send email when user clicks on forget password.
     *
     * @param email Variable is matched with the existing emails in the repository.
     *              If match found, a mail is triggered to the user to reset the
     *              password.
     * @return String Object to print the message.
     */

    @PostMapping("/forget-password")
    public ResponseEntity<String> forgetPassword(@RequestParam(name = "email") String email) {
        log.info("Inside forgetPassword controller method.");
        return new ResponseEntity<>(userService.forgetPassword(email), HttpStatus.OK);
    }

}
