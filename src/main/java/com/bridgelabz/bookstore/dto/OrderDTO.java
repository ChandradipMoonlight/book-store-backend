package com.bridgelabz.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private LocalDate orderDate;
    private int orderQuantity;
    private int orderPrice;
    private String address;
    private int userId;
    private int bookId;
    private boolean cancel;
}
