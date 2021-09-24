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

    @Override
    public List<OrderModel> getAllOrder(String token) {
        log.info("Inside the getAllOrder method of the OrderService Class.");
        int userId = tokenUtil.decodeToken(token);
        Optional<UserModel> isUserPresent = userRepository.findById(userId);
        if (isUserPresent.isPresent()) {
//            Optional<OrderModel> order = orderRepository.findById(isUserPresent.get().getUserId());

//            if (!order.get().isCancelled()) {
                return  orderRepository.findAll();
//            } else
//                throw new BookStoreException("No order is placed.");
        } else
            throw new BookStoreException(MessageProperties.PLEASE_LOGIN.getMessage());
    }

    @Override
    public List<OrderModel> getAllOrderForUser(String token, int userId) {
        log.info("Inside the getAllOrder method of the OrderService Class.");
        int getUserIdByToken = tokenUtil.decodeToken(token);
        Optional<UserModel> isUserPresent = userRepository.findById(getUserIdByToken);
        if (isUserPresent.isPresent()) {
         if (isUserPresent.get().getUserId() == userId) {
             return orderRepository.findAllByUserModel(isUserPresent.get());
//             return new ResponseDTO("All orders for user is retrieved successfully.",
//                     orderRepository.findAllByUserModel(isUserPresent.get()));
         } else
             throw new BookStoreException("No order is placed");
        } else
            throw new BookStoreException(MessageProperties.PLEASE_LOGIN.getMessage());
    }
}
