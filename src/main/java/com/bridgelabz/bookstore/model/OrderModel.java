package com.bridgelabz.bookstore.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Data
public class OrderModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderId;
    private LocalDate orderDate;
    private int orderQuantity;
    private int orderPrice;
    private String address;


    private int userId;
    private int bookId;
    private boolean cancel;
}
