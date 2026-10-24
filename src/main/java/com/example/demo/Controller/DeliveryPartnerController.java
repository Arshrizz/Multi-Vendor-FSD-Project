package com.example.demo.Controller;


import com.example.demo.Entity.DeliveryPartner;
import com.example.demo.Repository.DeliveryPartnerRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/delivery-partners")
@Tag(name = "Delivery Partner Controller", description = "CRUD Endpoints for Delivery Partners")
public class DeliveryPartnerController {

    @Autowired
    private DeliveryPartnerRepository repository;

    // 1. POST - Create new partner
    @PostMapping
    @Operation(summary = "Create a new delivery partner")
    public ResponseEntity<DeliveryPartner> createPartner(@RequestBody DeliveryPartner partner) {
        return ResponseEntity.ok(repository.save(partner));
    }

    // 2. GET - Fetch all partners
    @GetMapping
    @Operation(summary = "Get all delivery partners")
    public List<DeliveryPartner> getAllPartners() {
        return repository.findAll();
    }

    // 3. GET - Find by ID
    @GetMapping("/{id}")
    @Operation(summary = "Find a delivery partner by UUID")
    public ResponseEntity<DeliveryPartner> getPartnerById(@PathVariable UUID id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 4. PUT - Complete entity replacement
    @PutMapping("/{id}")
    @Operation(summary = "Update all details of a delivery partner")
    public ResponseEntity<DeliveryPartner> updatePartner(@PathVariable UUID id, @RequestBody DeliveryPartner updatedData) {
        return repository.findById(id).map(partner -> {
            partner.setUserId(updatedData.getUserId());
            partner.setVehicleNo(updatedData.getVehicleNo());
            partner.setCurrentLatitude(updatedData.getCurrentLatitude());
            partner.setCurrentLongitude(updatedData.getCurrentLongitude());
            partner.setCurrentStatus(updatedData.getCurrentStatus());
            partner.setAvailable(updatedData.isAvailable());
            return ResponseEntity.ok(repository.save(partner));
        }).orElse(ResponseEntity.notFound().build());
    }

    // 5. PATCH - Partial update (e.g., latitude/longitude update from GPS)
    @PatchMapping("/{id}/location")
    @Operation(summary = "Partially update driver location and status")
    public ResponseEntity<DeliveryPartner> updateLocation(
            @PathVariable UUID id,
            @RequestParam(required = false) BigDecimal latitude,
            @RequestParam(required = false) BigDecimal longitude,
            @RequestParam(required = false) String status) {

        return repository.findById(id).map(partner -> {
            if (latitude != null) partner.setCurrentLatitude(latitude);
            if (longitude != null) partner.setCurrentLongitude(longitude);
            if (status != null) partner.setCurrentStatus(status);
            return ResponseEntity.ok(repository.save(partner));
        }).orElse(ResponseEntity.notFound().build());
    }

    // 6. DELETE - Remove partner
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete delivery partner record")
    public ResponseEntity<Void> deletePartner(@PathVariable UUID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
