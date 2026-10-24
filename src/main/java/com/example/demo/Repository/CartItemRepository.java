package com.example.demo.Repository;

import com.example.demo.Entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {
}