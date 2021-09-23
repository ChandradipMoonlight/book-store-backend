package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.dto.CartDTO;
import com.bridgelabz.bookstore.dto.ResponseDTO;

public interface ICartService {

    ResponseDTO addToCart(String token, CartDTO cartDTO, int id);

}
