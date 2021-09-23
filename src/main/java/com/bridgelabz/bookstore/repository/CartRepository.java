package com.bridgelabz.bookstore.repository;

import com.bridgelabz.bookstore.model.CartModel;
import com.bridgelabz.bookstore.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<CartModel, Integer> {

    List<CartModel> findAllByUserModel(UserModel userModel);

}
