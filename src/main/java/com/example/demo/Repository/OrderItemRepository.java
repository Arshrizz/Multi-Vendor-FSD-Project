package com.example.demo.Repository.Orders;

import com.example.demo.Entity.Orders.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {
}