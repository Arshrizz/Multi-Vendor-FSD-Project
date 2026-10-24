package com.example.demo.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.VendorEntity;

public interface VendorRepository extends JpaRepository<VendorEntity, Integer> {
}
