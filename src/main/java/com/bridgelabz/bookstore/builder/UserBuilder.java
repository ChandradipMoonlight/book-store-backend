package com.bridgelabz.bookstore.builder;


import com.bridgelabz.bookstore.dto.UserDTO;
import com.bridgelabz.bookstore.model.UserRegistration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserBuilder {

    public UserRegistration buildDo(UserDTO userDTO) {
        log.info("Inside buildDo Method.");
        UserRegistration userRegistration = new UserRegistration();
        BeanUtils.copyProperties(userDTO, userRegistration);
        return userRegistration;
    }
}