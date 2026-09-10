package com.example.demo.Repository.Orders;

import com.example.demo.Entity.Orders.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {
}