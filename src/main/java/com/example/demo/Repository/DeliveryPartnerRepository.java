package com.example.demo.Repository;

import com.example.demo.Entity.DeliveryPartner;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface DeliveryPartnerRepository extends JpaRepository<DeliveryPartner, UUID> {}