package com.example.demo.Repository;

import com.example.demo.Entity.DeliveryTracking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface DeliveryTrackingRepository extends JpaRepository<DeliveryTracking, UUID> {}