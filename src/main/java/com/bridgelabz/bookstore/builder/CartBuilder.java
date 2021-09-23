package com.bridgelabz.bookstore.builder;


import com.bridgelabz.bookstore.dto.CartDTO;
import com.bridgelabz.bookstore.model.CartModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CartBuilder {
    public CartModel buildDo(CartDTO cartDTO) {
        log.info("Inside buildDo Method.");
        CartModel cartModel = new CartModel();
        BeanUtils.copyProperties(cartDTO, cartModel);
        return cartModel;
    }
}
