package com.example.demo.Entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "delivery_tracking")
public class DeliveryTracking {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "assignment_id", updatable = false, nullable = false)
    private UUID assignmentId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "assignment_status")
    private String assignmentStatus;

    @Column(name = "pickup_vendor")
    private String pickupVendor;

    @Column(name = "pickup_user")
    private String pickupUser;

    @Column(name = "eta")
    private String eta;

    // Many-to-One Relationship with DeliveryPartner
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "partner_id", nullable = false)
    private DeliveryPartner deliveryPartner;

    // Constructors
    public DeliveryTracking() {}

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

    public String getEta() { return eta; }
    public void setEta(String eta) { this.eta = eta; }

    public DeliveryPartner getDeliveryPartner() { return deliveryPartner; }
    public void setDeliveryPartner(DeliveryPartner deliveryPartner) { this.deliveryPartner = deliveryPartner; }
}