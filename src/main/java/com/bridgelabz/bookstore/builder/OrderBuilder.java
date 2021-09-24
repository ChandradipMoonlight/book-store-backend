package com.bridgelabz.bookstore.builder;

import com.bridgelabz.bookstore.dto.OrderDTO;
import com.bridgelabz.bookstore.model.OrderModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderBuilder {

    public OrderModel buildDo(OrderDTO orderDTO) {
         log.info("inside buildDo method of the OrderBuilder Class.");
         OrderModel orderModel = new OrderModel();
        BeanUtils.copyProperties(orderDTO, orderModel);
        return orderModel;
    }
}
