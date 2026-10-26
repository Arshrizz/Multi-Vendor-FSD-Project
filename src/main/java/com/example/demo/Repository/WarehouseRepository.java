package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.WarehouseEntity;

public interface WarehouseRepository extends JpaRepository<WarehouseEntity, Integer>{
}