package com.example.demo.Controller;

import com.example.demo.Entity.DeliveryPartner;
import com.example.demo.Repository.DeliveryPartnerRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/delivery-partners")
@Tag(name = "Delivery Partner Controller", description = "CRUD Endpoints for Delivery Partners")
public class DeliveryPartnerController {

    @Autowired
    private DeliveryPartnerRepository repository;

    @PostMapping
    @Operation(summary = "Create a new delivery partner")
    public ResponseEntity<DeliveryPartner> createPartner(@RequestBody DeliveryPartner partner) {
        return ResponseEntity.ok(repository.save(partner));
    }

    @GetMapping
    @Operation(summary = "Get all delivery partners")
    public List<DeliveryPartner> getAllPartners() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find a delivery partner by ID")
    public ResponseEntity<DeliveryPartner> getPartnerById(@PathVariable UUID id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update all details of a delivery partner")
    public ResponseEntity<DeliveryPartner> updatePartner(@PathVariable UUID id, @RequestBody DeliveryPartner updatedData) {
        return repository.findById(id).map(partner -> {
            partner.setUserId(updatedData.getUserId());
            partner.setVehicleNo(updatedData.getVehicleNo());
            partner.setCurrentLatitude(updatedData.getCurrentLatitude());
            partner.setCurrentLongitude(updatedData.getCurrentLongitude());
            partner.setCurrentStatus(updatedData.getCurrentStatus());
            partner.setDeliveredAt(updatedData.getDeliveredAt());
            partner.setAvailable(updatedData.isAvailable());
            return ResponseEntity.ok(repository.save(partner));
        }).orElse(ResponseEntity.notFound().build());
    }

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