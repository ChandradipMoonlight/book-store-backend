package com.bridgelabz.bookstore.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class CartModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int quantity;

    @OneToOne
    @JoinColumn(name = "id")
    public UserModel user;
}
