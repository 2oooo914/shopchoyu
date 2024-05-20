package com.likelion.shopchoyu.repository;

import com.likelion.shopchoyu.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByNameIsContaining(String keyword);

    List<Order> findAllByUser_Email(String email);
}