package com.bridgelabz.bookstore.builder;


import com.bridgelabz.bookstore.dto.UserDTO;
import com.bridgelabz.bookstore.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserBuilder {

    public UserModel buildDo(UserDTO userDTO) {
        log.info("Inside buildDo Method.");
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDTO, userModel);
        return userModel;
    }
}