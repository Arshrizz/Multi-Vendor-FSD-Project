package com.example.demo.Entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "deliverypartner")
public class DeliveryPartner {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "partner_id")
    private UUID partnerId;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "vehicle_no", nullable = false, unique = true)
    private String vehicleNo;

    @Column(name = "current_latitude")
    private BigDecimal currentLatitude;

    @Column(name = "current_longitude")
    private BigDecimal currentLongitude;

    @Column(name = "current_status", nullable = false)
    private String currentStatus = "offline";

    @Column(name = "is_available", nullable = false)
    private boolean isAvailable = true;

    public DeliveryPartner() {}

    public DeliveryPartner(Long userId, String vehicleNo, BigDecimal currentLatitude, BigDecimal currentLongitude, String currentStatus, boolean isAvailable) {
        this.userId = userId;
        this.vehicleNo = vehicleNo;
        this.currentLatitude = currentLatitude;
        this.currentLongitude = currentLongitude;
        this.currentStatus = currentStatus;
        this.isAvailable = isAvailable;
    }

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

    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }
}