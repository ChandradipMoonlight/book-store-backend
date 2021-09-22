package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.builder.MessageProperties;
import com.bridgelabz.bookstore.builder.UserBuilder;
import com.bridgelabz.bookstore.dto.UserDTO;
import com.bridgelabz.bookstore.dto.UserLoginDTO;
import com.bridgelabz.bookstore.exception.BookStoreException;
import com.bridgelabz.bookstore.model.UserModel;
import com.bridgelabz.bookstore.repository.UserRepository;
import com.bridgelabz.bookstore.utils.MailUtil;
import com.bridgelabz.bookstore.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@Service
public class UserService implements IUserService {

    @Autowired
    private UserBuilder userBuilder;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MailUtil mailUtil;
    @Autowired
    private TokenUtil tokenUtil;

    /**
     * Purpose : Ability to add user in the database.
     *           This method is responsible to take use input and send email to that user
     *           to for verification.
     *           When use inter the details for registration and hit enter it will check first
     *           whether the email is already present or not in database, if yes then it will throw exception that
     *           "User Email id is already present, Please try with different Email id."  and if
     *           not present then it will save the details in the database with encoded password.
     *           And This method is also send the verification email to the user with token.
     *
     * @param userDTO UserRegistrationDTO object to store it in the repository.
     * @return String Object to print the message.
     */

    @Override
    public String createUserRegistration(UserDTO userDTO) {
        log.info("Inside the createUserRegistration method of UserService Class.");
        String toEmail = userDTO.getEmail();
        Optional<UserModel> byEmailId = userRepository.findByEmail(userDTO.getEmail());

        if (byEmailId.isPresent()) {
            throw new BookStoreException("User Email Id is already present, Please try with different Email Id.",
                    BookStoreException.ExceptionType.USER_ALREADY_PRESENT);
        } else {
            String password = bCryptPasswordEncoder.encode(userDTO.getPassword());
            userDTO.setPassword(password);
            UserModel userModel = userBuilder.buildDo(userDTO);
            userRepository.save(userModel);
        }

        String generatedToken = tokenUtil.generateVerificationToken(toEmail);
        String body ="Dear" + userDTO.getFirstName() + ",\n Please click on the given link to complete the registration.\n " +
                "http://localhost:8080/verifyEmail?token=" + generatedToken;
        System.out.println(body);
        try {
            String subject = "Complete the Registration ";
            mailUtil.sendEmail(toEmail, subject, body);
        } catch (MessagingException exception) {
            exception.printStackTrace();
        }
        return MessageProperties.REGISTRATION_SUCCESSFUL.getMessage();
    }

    /**
     * Purpose : Ability to verify email after registration.
     *           This method is used to verify the details which was given by the user.
     *           when use click on the url which he got through email it will get verified.
     *
     * @param token  Object received from the get url.
     *               The token is further matched with the user email,
     *               and the verified column is set as true or false.
     *
     * @return      String Object to print the message.
     */

    @Override
    public String verifyEmailByToken(String token) {
        log.info("Inside verifyEmail service method.");
        UserModel userModel = getUserByEmailToken(token);
        userModel.setVerified(true);
        userRepository.save(userModel);
        log.info("verifyEmail service method successfully executed.");
        return MessageProperties.EMAIL_VERIFIED.getMessage();
    }

    /**
     * Purpose : Ability to login user after validating details.
     *
     * @param userLoginDTO Object accepts email and password from user.
     *                     If matches user logs in successfully.
     *
     * @return userLoginDTO object.
     */

    @Override
    public String loginUser(UserLoginDTO userLoginDTO) {
        log.info("Inside loginUser service method.");
        UserModel userByEmail = userRepository.findByEmail(
                userLoginDTO.getEmail()).orElseThrow(
                () -> new BookStoreException("User with this email is not registered",
                        BookStoreException.ExceptionType.EMAIL_NOT_FOUND));
        if (userByEmail.isVerified()) {
            boolean password = bCryptPasswordEncoder.matches(userLoginDTO.getPassword(),
                    userByEmail.getPassword());
            if (!password) {
                throw new BookStoreException("Incorrect password",
                        BookStoreException.ExceptionType.PASSWORD_INCORRECT);
            }
            log.info("loginUser service method successfully executed.");
            return userLoginDTO.getEmail();
        } else {
            throw new BookStoreException("Not verified",
                    BookStoreException.ExceptionType.EMAIL_NOT_VERIFIED);
        }
    }

    /**
     * Purpose : Ability to send email when user clicks on forget password.
     *
     * @param email Variable is matched with the existing emails in the repository.
     *              If match found, a mail is triggered to the user to reset the
     *              password.
     * @return String Object to print the message.
     */

    @Override
    public String forgetPassword(String email) {
        log.info("Inside forgetPassword service method.");
        UserModel userByEmail = userRepository.findByEmail(
                email).orElseThrow(
                () -> new BookStoreException("User with this email is not registered",
                        BookStoreException.ExceptionType.EMAIL_NOT_FOUND));
        if (userByEmail.isVerified()) {
            try {
                String subject = "Reset Password";
                String body ="Click on the link to reset password\n" + "http://localhost:8080/verifyEmail?token=" + tokenUtil.generateVerificationToken(email);
                mailUtil.sendEmail(email,  subject, body);
            } catch (MessagingException exception) {
                exception.printStackTrace();
            }
        } else {
            throw new BookStoreException("Not verified",
                    BookStoreException.ExceptionType.EMAIL_NOT_VERIFIED);
        }
        log.info("forgetPassword service method successfully executed.");
        return MessageProperties.FORGET_PASSWORD.getMessage();
    }

    /**
     * Purpose : Ability to reset the password.
     *
     * @param token Object received from the get url.
     *               The token is further matched with the user email.
     *
     * @param password  User sets the password once the token is matched.
     *                 It will update the password of user in repository.
     * @return String object of the message.
     */
    @Override
    public String resetPassword(String token, String password) {
        log.info("Inside resetPassword service method.");
        UserModel user = getUserByEmailToken(token);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(user);
        log.info("resetPassword service method successfully executed.");
        return MessageProperties.RESET_PASSWORD.getMessage();
    }



    private UserModel getUserByEmailToken(String token) {
        String email = tokenUtil.parseToken(token);
        return userRepository.findByEmail(email).orElseThrow(
                () -> new BookStoreException("Unauthorised User",
                        BookStoreException.ExceptionType.UNAUTHORISED_USER));
    }
}
