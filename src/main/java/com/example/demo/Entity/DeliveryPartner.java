package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "delivery_partner")
public class DeliveryPartner {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "partner_id", updatable = false, nullable = false)
    private UUID partnerId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "vehicle_no")
    private String vehicleNo;

    @Column(name = "current_latitude", precision = 10, scale = 8)
    private BigDecimal currentLatitude;

    @Column(name = "current_longitude", precision = 11, scale = 8)
    private BigDecimal currentLongitude;

    @Column(name = "current_status")
    private String currentStatus;

    @Column(name = "delivered_at")
    private LocalDateTime deliveredAt;

    @Column(name = "is_available")
    private boolean isAvailable;

    // One-to-Many Relationship with DeliveryTracking
    @OneToMany(mappedBy = "deliveryPartner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore // Prevents infinite recursion during REST serialization
    private List<DeliveryTracking> trackingAssignments = new ArrayList<>();

    // Constructors
    public DeliveryPartner() {}

    // Getters and Setters
    public UUID getPartnerId() { return partnerId; }
    public void setPartnerId(UUID partnerId) { this.partnerId = partnerId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getVehicleNo() { return vehicleNo; }
    public void setVehicleNo(String vehicleNo) { this.vehicleNo = vehicleNo; }

    public BigDecimal getCurrentLatitude() { return currentLatitude; }
    public void setCurrentLatitude(BigDecimal currentLatitude) { this.currentLatitude = currentLatitude; }

    public BigDecimal getCurrentLongitude() { return currentLongitude; }
    public void setCurrentLongitude(BigDecimal currentLongitude) { this.currentLongitude = currentLongitude; }

    public String getCurrentStatus() { return currentStatus; }
    public void setCurrentStatus(String currentStatus) { this.currentStatus = currentStatus; }

    public LocalDateTime getDeliveredAt() { return deliveredAt; }
    public void setDeliveredAt(LocalDateTime deliveredAt) { this.deliveredAt = deliveredAt; }

    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }

    public List<DeliveryTracking> getTrackingAssignments() { return trackingAssignments; }
    public void setTrackingAssignments(List<DeliveryTracking> trackingAssignments) { this.trackingAssignments = trackingAssignments; }
}