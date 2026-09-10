package com.example.demo.Repository.Orders;

import com.example.demo.Entity.Orders.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartEntity, Long> {
}