package com.bridgelabz.bookstore.controller;


import com.bridgelabz.bookstore.dto.CartDTO;
import com.bridgelabz.bookstore.dto.ResponseDTO;
import com.bridgelabz.bookstore.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CartController {

    @Autowired
    private ICartService cartService;

    @PostMapping("/addtocart/{token}")
    public ResponseEntity<ResponseDTO> addToCart(@PathVariable String token,
                                                 @RequestBody CartDTO cartDTO, @RequestParam int id) {
        ResponseDTO responseDTO = cartService.addToCart(token, cartDTO, id);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
}
