package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.DeliveryTracking;

public interface DeliveryTrackingRepository extends JpaRepository<DeliveryTracking, Integer> {
    // Custom query methods can be defined here if needed

}
