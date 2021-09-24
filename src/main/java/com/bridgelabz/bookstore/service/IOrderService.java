package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.dto.OrderDTO;
import com.bridgelabz.bookstore.dto.ResponseDTO;
import com.bridgelabz.bookstore.model.OrderModel;

import java.util.List;

public interface IOrderService {

    ResponseDTO placeOrder(String token, OrderDTO orderDTO);

    String cancelOrder(String token, int orderId);

    List<OrderModel> getAllOrder(String token);

    List<OrderModel> getAllOrderForUser(String token, int userId);
}
