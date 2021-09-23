package com.bridgelabz.bookstore.controller;


import com.bridgelabz.bookstore.builder.MessageProperties;
import com.bridgelabz.bookstore.dto.CartDTO;
import com.bridgelabz.bookstore.dto.ResponseDTO;
import com.bridgelabz.bookstore.model.CartModel;
import com.bridgelabz.bookstore.service.ICartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class CartController {

    @Autowired
    private ICartService cartService;

    /**
     * Purpose:- Add the book Items in the CartRepository;
     *          This method will add the books in the cart when token get validated.
     *
     * @param token this is jwt token generated at the time of user registration with
     *              the help of the userId.
     *
     * @param cartDTO Object of the CartDTO Class.
     * @return response of method as Object of the cartDTO and String object message.
     */

    @PostMapping("/add-to-cart}")
    public ResponseEntity<ResponseDTO> addToCart(@RequestParam String token,
                                                 @RequestBody CartDTO cartDTO) {
        log.info("Inside the addToCart method of the CartController Class.");
        ResponseDTO responseDTO = cartService.addToCart(token, cartDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    /**
     * Purpose:- To fetch the cart Items from the database.
     *           this method will retrieve all teh items for the cart with the object
     *           of the user and the book models.
     *
     * @param token this is jwt token generated at the time of user registration with
     *              the help of the userId.
     *
     * @return response of the method as object of CartModel with object of string message.
     */

    @GetMapping("/get-all-cart-items")
    public ResponseEntity<ResponseDTO> getAllCartItems(@RequestParam String token) {
        log.info("Inside the getAllCartItems method of the CartController Class.");
        List<CartModel> cartItemsList = cartService.getAllCartItems(token);
        return new ResponseEntity<>(new ResponseDTO(MessageProperties.GET_CART_ITEMS.getMessage(), cartItemsList),  HttpStatus.OK);
    }

    /**
     * Purpose:- Delete the Items from the Cart.
     *           this method will delete the items for particular cartId  from the cart repository
     *           when token is validated.
     *
     * @param token this is jwt token generated at the time of user registration with
     *              the help of the userId.
     *
     * @param cartId as input to delete the items from the cart.
     *
     * @return response with object of the string message.
     */

    @DeleteMapping("/delete-form-cart")
    public ResponseEntity<String> deleteItemsFromCart(@RequestParam String token, @RequestParam int cartId) {
        log.info("Inside the deleteItemsFromCart method of the CartController Class.");
        return new ResponseEntity<>(cartService.deleteItemsFromCart(token, cartId), HttpStatus.OK);
    }

    /**
     * Purpose:- Ability to update the quantity of the items in cart for particular cart id.
     *
     * @param token this is jwt token generated at the time of user registration with
     *              the help of the userId.
     *
     * @param cartId input to update the quantity of the items for this cart id.
     *
     * @param cartDTO object of the CartDTO class.
     *
     * @return response with object of the string message.
     */

    @PutMapping("/update-quantity-in-cart")
    public ResponseEntity<String> updateQuantityInCart(@RequestParam String token , @RequestParam int cartId, @RequestBody CartDTO cartDTO) {
        log.info("Inside the method of the updateQuantityInCart of the CartController Class.");
        return  new ResponseEntity<>(cartService.updateQuantityOfItemsInCart(token, cartId, cartDTO), HttpStatus.OK);
    }

    /**
     * Purpose:- Ability to fetch cart items for associated with particular user only.
     *           this method will retrieve all teh cart items associated with userId when token
     *           is get validated.
     *
     * @param token this is jwt token generated at the time of user registration with
     *              the help of the userId.
     *
     * @param userId passed as input user id to fetch all the cart items related to this id only.
     *
     * @return response with object of the string message.
     */

    @GetMapping("/get-cart-items-for-user")
    public ResponseEntity<ResponseDTO> getCartItemsForUser(@RequestParam String token, @RequestParam int userId) {
        log.info("Inside the method of the getCartItemsForUser of the CartController Class.");
        List<CartModel> carItemsListForUser = cartService.getCartItemsByUserId(token, userId);
        return new ResponseEntity<>(new ResponseDTO(MessageProperties.GET_CART_ITEMS.getMessage(), carItemsListForUser), HttpStatus.OK);
    }

}
