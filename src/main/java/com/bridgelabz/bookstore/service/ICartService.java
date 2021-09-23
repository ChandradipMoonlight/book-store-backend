package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.dto.CartDTO;
import com.bridgelabz.bookstore.dto.ResponseDTO;
import com.bridgelabz.bookstore.model.CartModel;

import java.util.List;

public interface ICartService {

    ResponseDTO addToCart(String token, CartDTO cartDTO);

    List<CartModel> getAllCartItems(String token);

    String deleteItemsFromCart(String token, int carId);

    String updateQuantityOfItemsInCart(String token, int cartId, CartDTO cartDTO);

    List<CartModel> getCartItemsByUserId(String token, int userId);

}
