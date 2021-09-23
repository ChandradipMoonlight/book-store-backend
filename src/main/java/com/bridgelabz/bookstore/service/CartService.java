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
