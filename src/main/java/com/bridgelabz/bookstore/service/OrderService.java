package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.builder.MessageProperties;
import com.bridgelabz.bookstore.builder.OrderBuilder;
import com.bridgelabz.bookstore.dto.OrderDTO;
import com.bridgelabz.bookstore.dto.ResponseDTO;
import com.bridgelabz.bookstore.exception.BookStoreException;
import com.bridgelabz.bookstore.model.BookModel;
import com.bridgelabz.bookstore.model.OrderModel;
import com.bridgelabz.bookstore.model.UserModel;
import com.bridgelabz.bookstore.repository.BookRepository;
import com.bridgelabz.bookstore.repository.OrderRepository;
import com.bridgelabz.bookstore.repository.UserRepository;
import com.bridgelabz.bookstore.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderService implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderBuilder orderBuilder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private BookRepository bookRepository;

    /**
     *Purpose:- Ability to place the order when user is verified.
     *          this method is first check take the token and decode it and check whether the
     *          user is verified or not if yes, it will check the book is selected or not if yes,
     *          it will place the order successfully. else it will throw a costume exception.
     *          that please log in first.
     *
     * @param token it is generated when user is logged in. it passed as input to check the
     *              user is verified or not.
     *
     * @param orderDTO this object of the OrderDTO class which as all information related to
     *                 place the order.
     * @return response as object of the OrderDTO class and String message.
     */

    @Override
    public ResponseDTO placeOrder(String token, OrderDTO orderDTO) {
        log.info("Inside the placeOrder method of the OrderService Class.");
        int userId = tokenUtil.decodeToken(token);
        Optional<UserModel> isUserPresent = userRepository.findById(userId);
        if (isUserPresent.isPresent()) {
            Optional<BookModel> book = bookRepository.findById(orderDTO.getBookId());

            if (book.isPresent()) {
                OrderModel orderModel = orderBuilder.buildDo(orderDTO);
                orderModel.setBookModel(book.get());
                orderModel.setUserModel(isUserPresent.get());
                orderRepository.save(orderModel);
                return new ResponseDTO("Oder placed Successfully.", orderRepository.save(orderModel));
            } else
                throw new BookStoreException("Please select the books to place order");
        } else
            throw new BookStoreException(MessageProperties.PLEASE_LOGIN.getMessage());
    }

    /**
     *Purpose:- Ability to cancel the order.
     *          this method will cancel the order for the particular id when token get verified.
     *
     * @param token is generated when user is logged in. and passed as input to verify the user.
     *
     * @param orderId is used to cancel the order for particular id only.
     *
     * @return object of the String message.
     */

    @Override
    public String cancelOrder(String token, int orderId) {
        log.info("Inside the CancelOrder method of the OrderService Class.");
        int userId = tokenUtil.decodeToken(token);
        Optional<UserModel> isUserPresent = userRepository.findById(userId);
        if (isUserPresent.isPresent()) {
            Optional<OrderModel> order = orderRepository.findById(orderId);
            if (order.isPresent()) {
                order.get().setCancelled(true);
                orderRepository.save(order.get());
                return MessageProperties.ORDER_CANCELLED.getMessage();
            } else
                throw new BookStoreException("No order is found");
        } else
            throw new BookStoreException(MessageProperties.PLEASE_LOGIN.getMessage());
    }

    /**
     * Purpose:- Ability to get all orders which are not cancelled.
     *           this method first check the user is verified or not with the help of token, if not it
     *           will throw new Costume exception that "please log in first." and if the user is
     *           verified user it will filter the all non cancelled orders with the help of the java 8
     *           feature stream api and return it.
     *
     * @param token is passed to verify the user
     *
     * @return object of the string message.
     */

    @Override
    public List<OrderModel> getAllOrder(String token) {
        log.info("Inside the getAllOrder method of the OrderService Class.");
        int userId = tokenUtil.decodeToken(token);
        Optional<UserModel> isUserPresent = userRepository.findById(userId);
        if (isUserPresent.isPresent()) {
                return  orderRepository.findAll().stream()
                        .filter(cancel -> !cancel.isCancelled()).collect(Collectors.toList());
        } else
            throw new BookStoreException(MessageProperties.PLEASE_LOGIN.getMessage());
    }

    /**
     * Purpose:- Ability ot get all orders for particular user.
     *
     * @param token is generated at the time of the login. and it is passed as input to
     *              verify the user.
     *
     * @param userId input given to fetch all orders for the user.
     *
     * @return Object of the String message.
     */

    @Override
    public List<OrderModel> getAllOrderForUser(String token, int userId) {
        log.info("Inside the getAllOrder method of the OrderService Class.");
        int getUserIdByToken = tokenUtil.decodeToken(token);
        Optional<UserModel> isUserPresent = userRepository.findById(getUserIdByToken);
        if (isUserPresent.isPresent()) {
         if (isUserPresent.get().getUserId() == userId) {
             return orderRepository.findAllByUserModel(isUserPresent.get()).stream()
                     .filter(cancel -> !cancel.isCancelled()).collect(Collectors.toList());
         } else
             throw new BookStoreException("No order is placed");
        } else
            throw new BookStoreException(MessageProperties.PLEASE_LOGIN.getMessage());
    }
}
