package com.bridgelabz.bookstore.repository;

import com.bridgelabz.bookstore.model.OrderModel;
import com.bridgelabz.bookstore.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderModel, Integer> {
    List<OrderModel> findAllByUserModel(UserModel userModel);
}
