package com.bridgelabz.bookstore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class CartModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cartId;

    private int quantity;

    @JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private UserModel userModel;

    @JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookId")
    private BookModel bookModel;

//    public CartModel(int quantity, UserModel userModel, BookModel bookModel) {
//        this.quantity = quantity;
//        this.userModel = userModel;
//        this.bookModel = bookModel;
//    }

}
