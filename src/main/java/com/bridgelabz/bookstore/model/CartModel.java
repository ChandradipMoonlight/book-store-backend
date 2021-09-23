package com.bridgelabz.bookstore.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class CartModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cartId;

    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private UserModel userModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookId")
    private BookModel bookModel;
}
