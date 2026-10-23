package com.example.demo.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}