package com.example.demo.Controller;

import java.util.List;
import java.util.Optional;  

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.VendorEntity;
import com.example.demo.Repository.VendorRepository;

@RestController
@RequestMapping("/vendor")
public class VendorController {

    @Autowired
    private VendorRepository vendorRepository;

    // CREATE vendor
    @PostMapping
    public ResponseEntity<?> addVendor(@RequestBody VendorEntity vendor) {
        try {
            VendorEntity savedVendor = vendorRepository.save(vendor);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedVendor);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create vendor");
        }
    }

    // GET all vendors
    @GetMapping
    public ResponseEntity<?> getAllVendors() {
        try {
            List<VendorEntity> vendors = vendorRepository.findAll();
            return ResponseEntity.ok(vendors);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch vendors");
        }
    }

    // GET vendor by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getVendorById(@PathVariable Integer id) {
        try {
            Optional<VendorEntity> vendor = vendorRepository.findById(id);

            if (vendor.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Vendor not found with ID: " + id);
            }

            return ResponseEntity.ok(vendor.get());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch vendor");
        }
    }

    // UPDATE vendor
    @PutMapping("/{id}")
    public ResponseEntity<?> updateVendor(
            @PathVariable Integer id,
            @RequestBody VendorEntity vendor) {
        try {
            Optional<VendorEntity> existing = vendorRepository.findById(id);

            if (existing.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Vendor not found with ID: " + id);
            }

            vendor.setVendorId(id);
            VendorEntity updatedVendor = vendorRepository.save(vendor);

            return ResponseEntity.ok(updatedVendor);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update vendor");
        }
    }

    // PARTIALLY UPDATE vendor
    @PatchMapping("/{id}")
    public ResponseEntity<?> patchVendor(
            @PathVariable Integer id,
            @RequestBody VendorEntity vendor) {
        try {
            Optional<VendorEntity> result = vendorRepository.findById(id);

            if (result.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Vendor not found with ID: " + id);
            }

            VendorEntity existingVendor = result.get();

            if (vendor.getUserId() != null) {
                existingVendor.setUserId(vendor.getUserId());
            }

            if (vendor.getAssignmentId() != null) {
                existingVendor.setAssignmentId(vendor.getAssignmentId());
            }

            if (vendor.getStoreName() != null) {
                existingVendor.setStoreName(vendor.getStoreName());
            }

            VendorEntity updatedVendor = vendorRepository.save(existingVendor);
            return ResponseEntity.ok(updatedVendor);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to patch vendor");
        }
    }

    // DELETE vendor
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVendor(@PathVariable Integer id) {
        try {
            if (!vendorRepository.existsById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Vendor not found with ID: " + id);
            }

            vendorRepository.deleteById(id);

            return ResponseEntity.ok("Vendor deleted successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete vendor");
        }
    }
} 
