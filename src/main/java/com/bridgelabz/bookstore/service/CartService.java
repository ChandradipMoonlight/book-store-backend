package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.builder.CartBuilder;
import com.bridgelabz.bookstore.builder.MessageProperties;
import com.bridgelabz.bookstore.dto.CartDTO;
import com.bridgelabz.bookstore.dto.ResponseDTO;
import com.bridgelabz.bookstore.exception.BookStoreException;
import com.bridgelabz.bookstore.model.BookModel;
import com.bridgelabz.bookstore.model.CartModel;
import com.bridgelabz.bookstore.model.UserModel;
import com.bridgelabz.bookstore.repository.BookRepository;
import com.bridgelabz.bookstore.repository.CartRepository;
import com.bridgelabz.bookstore.repository.UserRepository;
import com.bridgelabz.bookstore.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CartService implements ICartService{

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartBuilder cartBuilder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private BookRepository bookRepository;

    /**
     * Purpose:- Ability to add the books details into the book repository.
     *
     * @param token passed as input to verify the user.
     *
     * @param cartDTO object of the Cart DTO class.
     *
     * @return response of the method as object data and string message.
     */

    @Override
    public ResponseDTO addToCart(String token, CartDTO cartDTO) {
        log.info("Inside the addToCartMethod of the CartService Class.");
        int userId = tokenUtil.decodeToken(token);
        Optional<UserModel> isUserPresent = userRepository.findById(userId);
        if (isUserPresent.isPresent()) {
            Optional<BookModel> book = bookRepository.findById(cartDTO.getBookId());
            if (book.isPresent()) {
                CartModel cartModel = cartBuilder.buildDo(cartDTO);
                cartModel.setBookModel(book.get());
                cartModel.setUserModel(isUserPresent.get());
                cartRepository.save(cartModel);
                return new ResponseDTO("Books added in cart Successfully.", cartDTO);
            } else {
                throw new BookStoreException(MessageProperties.BOOK_NOT_FOUND.getMessage());
            }
        } else {
            throw new BookStoreException(MessageProperties.UNAUTHORISED_USER.getMessage());
        }
    }

    /**
     * Purpose:- Ability get all Cart Items when user get verified.
     *
     * @param token is passed to validate the user.
     *
     * @return Object the of the data.
     */

    @Override
    public List<CartModel> getAllCartItems(String token) {
        log.info("Inside the getAllCartItems method of the CartService Class.");
        int userId = tokenUtil.decodeToken(token);
        Optional<UserModel> isUserPresent = userRepository.findById(userId);
        if (isUserPresent.isPresent()) {
           return cartRepository.findAll();
        } else {
            throw new BookStoreException(MessageProperties.UNAUTHORISED_USER.getMessage());
        }
    }

    /**
     * Purpose:- Ability to delete the items from the cart for particular cartId.
     *
     * @param token is passed to validate the user.
     *
     * @param cartId is used to delete the items form the cart for cartId.
     *
     * @return response of the object of the string message.
     */

    @Override
    public String deleteItemsFromCart(String token, int cartId) {
        log.info("Inside the deleteItemsFromCart of the CartService Class.");
        int userId = tokenUtil.decodeToken(token);
        Optional<UserModel> isUserPresent = userRepository.findById(userId);
        if (isUserPresent.isPresent()) {
            cartRepository.deleteById(cartId);
            return MessageProperties.CART_ITEMS_DELETED.getMessage();
        } else
            throw new BookStoreException(MessageProperties.UNAUTHORISED_USER.getMessage());
    }

    /**
     * Purpose:- Ability to update the quantity of the book items in the cart.
     *
     * @param token is passed to validate the user.
     *
     * @param cartId is passed to check the book is present in the cart or not.
     *               if book is present int the cart then this method will update
     *               the book quantitiy.
     *
     * @param cartDTO object of the CartDTO class.
     *
     * @return response of the method as object of the string message.
     */

    @Override
    public String updateQuantityOfItemsInCart(String token, int cartId, CartDTO cartDTO) {
        log.info("Inside the deleteItemsFromCart of the CartService Class.");
        int userId = tokenUtil.decodeToken(token);
        Optional<UserModel> isUserPresent = userRepository.findById(userId);
        if (isUserPresent.isPresent()) {
            Optional<CartModel> isCartPresent = cartRepository.findById(cartId);
            if (isCartPresent.isPresent()) {
                isCartPresent.get().setQuantity(cartDTO.getQuantity());
                cartRepository.save(isCartPresent.get());
                return MessageProperties.UPDATED_BOOK_QUANTITY.getMessage();
            } else
                throw new BookStoreException(MessageProperties.CART_NOT_FOUND.getMessage());
        } else
            throw new BookStoreException(MessageProperties.UNAUTHORISED_USER.getMessage());
    }

    /**
     * Purpose: Ability to get the items form the cart for the particular user.
     *
     * @param token is passed to verify the user.
     *
     * @param userId is passed to fetch the items form the cart for the particular user.
     *
     * @return list of the Object of the CartModel.
     */

    @Override
    public List<CartModel> getCartItemsByUserId(String token, int userId) {
        log.info("Inside the deleteItemsFromCart of the CartService Class.");
        int getUserIdFormToken = tokenUtil.decodeToken(token);
        Optional<UserModel> isUserPresent = userRepository.findById(getUserIdFormToken);
        if (isUserPresent.isPresent()) {
            if (userId == isUserPresent.get().getUserId()) {
                return cartRepository.findAllByUserModel(isUserPresent.get());
            } else
                throw new BookStoreException(MessageProperties.USER_NOT_FOUND.getMessage());
        } else
        throw new BookStoreException(MessageProperties.UNAUTHORISED_USER.getMessage());
    }
}
