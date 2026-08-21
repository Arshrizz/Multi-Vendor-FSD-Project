package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.AddressEntity;

public interface AddressRepository extends JpaRepository<AddressEntity, Integer> {

}