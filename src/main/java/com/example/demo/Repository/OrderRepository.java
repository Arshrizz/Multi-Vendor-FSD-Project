package com.example.demo.Repository.Orders;

import com.example.demo.Entity.Orders.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}