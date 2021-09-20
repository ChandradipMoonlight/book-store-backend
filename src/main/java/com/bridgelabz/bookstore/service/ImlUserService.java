package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.builder.MessageProperties;
import com.bridgelabz.bookstore.builder.UserBuilder;
import com.bridgelabz.bookstore.dto.UserDTO;
import com.bridgelabz.bookstore.exception.BookStoreException;
import com.bridgelabz.bookstore.model.UserRegistration;
import com.bridgelabz.bookstore.repository.UserRepository;
import com.bridgelabz.bookstore.utils.MailUtil;
import com.bridgelabz.bookstore.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Optional;

@Slf4j
@Service
public class ImlUserService implements IUserService {

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

    @Override
    public String createUserRegistration(UserDTO userDTO) {
        log.info("Inside the createUserRegistration method of UserService Class.");
        String toEmail = userDTO.getEmail();
        Optional<UserRegistration> byEmailId = userRepository.findByEmail(userDTO.getEmail());
        if (byEmailId.isPresent()) {
            throw new BookStoreException("User Email Id is already present, Please try with different Email Id.",
                    BookStoreException.ExceptionType.USER_ALREADY_PRESENT);
        }

        String password = bCryptPasswordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(password);
        UserRegistration userRegistration = userBuilder.buildDo(userDTO);
        userRepository.save(userRegistration);

        String generatedToken = tokenUtil.generateVerificationToken(toEmail);
        String body = "http://localhost:8080/verifyemail?token=" + generatedToken;
        System.out.println(body);
        try {
            String subject = "Complete the Registration ";
            String displayMessage = "VERIFY";
            mailUtil.sendEmail(toEmail, generatedToken, subject, displayMessage);
        } catch (MessagingException exception) {
            exception.printStackTrace();
        }
        return MessageProperties.REGISTRATION_SUCCESSFUL.getMessage();
    }

    @Override
    public String verifyEmail(String token) {
        log.info("Inside verifyEmail service method.");
        UserRegistration userRegistration = getUserByEmailToken(token);
        userRegistration.setVerify(true);
        userRepository.save(userRegistration);
        log.info("verifyEmail service method successfully executed.");
        return MessageProperties.EMAIL_VERIFIED.getMessage();
    }

    private UserRegistration getUserByEmailToken(String token) {
        String email = tokenUtil.parseToken(token);
        return userRepository.findByEmail(email).orElseThrow(
                () -> new BookStoreException("Unauthorised User",
                        BookStoreException.ExceptionType.UNAUTHORISED_USER));
    }
}
