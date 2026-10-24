package com.example.demo.Entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "deliverytracking")
public class DeliveryTracking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "assignment_id")
    private UUID assignmentId; 

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "assignment_status", nullable = false)
    private String assignmentStatus = "pending";

    @Column(name = "pickup_vendor", nullable = false)
    private String pickupVendor;

    @Column(name = "pickup_user", nullable = false)
    private String pickupUser;

    @Column(name = "eta")
    private OffsetDateTime eta;

    public DeliveryTracking() {}

    public DeliveryTracking(Long orderId, String assignmentStatus, String pickupVendor, String pickupUser, OffsetDateTime eta) {
        this.orderId = orderId;
        this.assignmentStatus = assignmentStatus;
        this.pickupVendor = pickupVendor;
        this.pickupUser = pickupUser;
        this.eta = eta;
    }

    // Getters and Setters
    public UUID getAssignmentId() { return assignmentId; }
    public void setAssignmentId(UUID assignmentId) { this.assignmentId = assignmentId; }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public String getAssignmentStatus() { return assignmentStatus; }
    public void setAssignmentStatus(String assignmentStatus) { this.assignmentStatus = assignmentStatus; }

    public String getPickupVendor() { return pickupVendor; }
    public void setPickupVendor(String pickupVendor) { this.pickupVendor = pickupVendor; }

    public String getPickupUser() { return pickupUser; }
    public void setPickupUser(String pickupUser) { this.pickupUser = pickupUser; }

    public OffsetDateTime getEta() { return eta; }
    public void setEta(OffsetDateTime eta) { this.eta = eta; }
}