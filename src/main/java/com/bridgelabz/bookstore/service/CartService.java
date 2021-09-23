package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.dto.CartDTO;
import com.bridgelabz.bookstore.dto.ResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class CartService implements ICartService{

    @Override
    public ResponseDTO addToCart(String token, CartDTO cartDTO, int id) {
        return null;
    }
}
