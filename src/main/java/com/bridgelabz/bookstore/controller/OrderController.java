package com.bridgelabz.bookstore.controller;


import com.bridgelabz.bookstore.builder.MessageProperties;
import com.bridgelabz.bookstore.dto.OrderDTO;
import com.bridgelabz.bookstore.dto.ResponseDTO;
import com.bridgelabz.bookstore.model.CartModel;
import com.bridgelabz.bookstore.model.OrderModel;
import com.bridgelabz.bookstore.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class OrderController {

    @Autowired
    private IOrderService orderService;

    /**
     * Purpose:- Ability to place the order.
     *           this method will place the order when token get is verified or else
     *           it will throw exception that please log in first.
     *
     * @param token is passed to verify the user. it is generated when user login
     *              successfully.
     *
     * @param orderDTO Object of the OrderDTO class it will save all the details
     *                regarding the order into the orderRepository.
     *
     * @return response as string message and the data object.
     */

    @PostMapping("/place-order")
    public ResponseEntity<ResponseDTO> placeOrder(@RequestParam String token,
                                                  @RequestBody OrderDTO orderDTO) {
        log.info("Inside the place order method of the OrderController.");

        ResponseDTO responseDTO = orderService.placeOrder(token, orderDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    /**
     * Purpose:- Ability to cancel the order.
     *           this method will cancel the order once the user is get validated.
     *
     * @param token is generated at the time of user log inned it passed as
     *              input to validate the user.
     *
     * @param orderId order will get cancel for this order id only.
     *
     * @return response as object of the string message.
     */

    @PostMapping("/cancelOrder")
    public ResponseEntity<String> cancelOrder(@RequestParam String token,
                                              @RequestParam int orderId) {
        log.info("Inside the cancelOrder method method of the OrderController.");
        return new ResponseEntity<>(orderService.cancelOrder(token, orderId), HttpStatus.CREATED);
    }

    /**
     * Purpose : Ability to get all list of orders from database
     *
     * @param token input given by user to authenticate user
     *
     * @return object as list of all orders and string message.
     */

    @GetMapping("/get-all-orders")
    public ResponseEntity<ResponseDTO> getAllOrder(@RequestParam String token) {
        log.info("Inside the getAllOrder method of the OrderController.");

        List<OrderModel> orderModelList = orderService.getAllOrder(token);
        return new ResponseEntity<>(new ResponseDTO(MessageProperties.GET_CART_ITEMS.getMessage(), orderModelList),  HttpStatus.OK);

    }

    /**
     * Purpose : To get all orders placed by particular user from database.
     *
     * @param token input given by user to authenticate user.
     *
     * @param userId all orders get retrieved for particular user with the help of this user id.
     *
     * @return response as object of list of all orders and String message.
     */

    @GetMapping("/get-all-order-for-user")
    public ResponseEntity<ResponseDTO> getAllOrderForUser(@RequestParam String token,
                                                          @RequestParam int userId) {
        log.info("Inside getAllOrdersForUser Controller Method");
        ResponseDTO responseDTO = new ResponseDTO("Fetched all orders for the user",
                orderService.getAllOrderForUser(token, userId));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}