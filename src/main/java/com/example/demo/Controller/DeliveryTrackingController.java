package com.example.demo.Controller;

import com.example.demo.Entity.DeliveryPartner;
import com.example.demo.Entity.DeliveryTracking;
import com.example.demo.Repository.DeliveryPartnerRepository;
import com.example.demo.Repository.DeliveryTrackingRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/delivery-tracking")
@Tag(name = "Delivery Tracking Controller", description = "Endpoints for managing delivery assignments and tracking")
public class DeliveryTrackingController {

    @Autowired
    private DeliveryTrackingRepository trackingRepository;

    @Autowired
    private DeliveryPartnerRepository partnerRepository;

    // Create a new tracking assignment linked to a specific delivery partner (One-to-Many Relationship handling)
    @PostMapping("/partner/{partnerId}")
    @Operation(summary = "Assign a new tracking/order record to a partner")
    public ResponseEntity<DeliveryTracking> createAssignment(
            @PathVariable UUID partnerId,
            @RequestBody DeliveryTracking trackingRequest) {

        return partnerRepository.findById(partnerId).map(partner -> {
            // Link the "Many" (Tracking) to the "One" (Partner)
            trackingRequest.setDeliveryPartner(partner); 
            
            // Note: Ensure your DeliveryTracking entity has fields matching the ER Diagram:
            // assignment_id, order_id, assignment_status, pickup_vendor, pickup_user, ETA
            return ResponseEntity.ok(trackingRepository.save(trackingRequest));
        }).orElse(ResponseEntity.notFound().build());
    }

    // Get all assignments for a specific partner
    @GetMapping("/partner/{partnerId}")
    @Operation(summary = "Get all tracking assignments for a specific delivery partner")
    public ResponseEntity<List<DeliveryTracking>> getAssignmentsByPartner(@PathVariable UUID partnerId) {
        // This leverages the One-to-Many relationship by fetching the list of tracking records linked to one partner
        return ResponseEntity.ok(trackingRepository.findByDeliveryPartnerPartnerId(partnerId));
    }
    
    // Update an assignment status
    @PatchMapping("/{assignmentId}/status")
    @Operation(summary = "Update the assignment status and ETA")
    public ResponseEntity<DeliveryTracking> updateTrackingStatus(
            @PathVariable UUID assignmentId,
            @RequestParam(required = false) String assignmentStatus,
            @RequestParam(required = false) String eta) {
            
        return trackingRepository.findById(assignmentId).map(tracking -> {
            if (assignmentStatus != null) tracking.setAssignmentStatus(assignmentStatus);
            if (eta != null) tracking.setEta(eta);
            return ResponseEntity.ok(trackingRepository.save(tracking));
        }).orElse(ResponseEntity.notFound().build());
    }
}