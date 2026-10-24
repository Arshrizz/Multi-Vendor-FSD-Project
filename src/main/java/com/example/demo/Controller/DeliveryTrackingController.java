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

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/delivery-tracking")
@Tag(name = "Delivery Tracking Controller", description = "Endpoints for live tracking updates")
public class DeliveryTrackingController {

    @Autowired
    private DeliveryTrackingRepository trackingRepository;

    @Autowired
    private DeliveryPartnerRepository partnerRepository;

    // Log a new location point for a partner
    @PostMapping("/partner/{partnerId}")
    @Operation(summary = "Log location update for a partner")
    public ResponseEntity<DeliveryTracking> logLocation(
            @PathVariable UUID partnerId,
            @RequestParam(required = false) Long orderId,
            @RequestParam BigDecimal latitude,
            @RequestParam BigDecimal longitude) {

        return partnerRepository.findById(partnerId).map(partner -> {
            // Also update the partner's current location directly
            partner.setCurrentLatitude(latitude);
            partner.setCurrentLongitude(longitude);
            partnerRepository.save(partner);

            DeliveryTracking tracking = new DeliveryTracking();
            tracking.setDeliveryPartner(partner);
            tracking.setOrderId(orderId);
            tracking.setLatitude(latitude);
            tracking.setLongitude(longitude);

            return ResponseEntity.ok(trackingRepository.save(tracking));
        }).orElse(ResponseEntity.notFound().build());
    }

    // Get location history for a specific partner
    @GetMapping("/partner/{partnerId}")
    @Operation(summary = "Get location history of a partner")
    public ResponseEntity<List<DeliveryTracking>> getPartnerHistory(@PathVariable UUID partnerId) {
        return ResponseEntity.ok(trackingRepository.findByDeliveryPartnerPartnerId(partnerId));
    }
}
