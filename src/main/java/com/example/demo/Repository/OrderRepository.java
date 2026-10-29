package com.example.demo.Repository;

import com.example.demo.Entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findByUser_IdOrderByOrderDateDesc(Integer userId);

    List<OrderEntity> findByStatus(String status);
}
