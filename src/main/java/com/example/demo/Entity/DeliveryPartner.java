package com.example.demo.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DeliveryPartner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int partner_id;

    private int user_id;
    private String vehicle_no;
    private float current_latitude;
    private float current_longitude;
    private String current_status;
    private boolean is_available;

    // Default constructor
    public DeliveryPartner() {
    }

    // Parameterized constructor
    public DeliveryPartner(int partner_id, int user_id, String vehicle_no,
                           float current_latitude, float current_longitude,
                           String current_status, boolean is_available) {

        this.partner_id = partner_id;
        this.user_id = user_id;
        this.vehicle_no = vehicle_no;
        this.current_latitude = current_latitude;
        this.current_longitude = current_longitude;
        this.current_status = current_status;
        this.is_available = is_available;
    }

    // Getters and Setters

    public int getPartnerId() {
        return partner_id;
    }

    public void setPartnerId(int partner_id) {
        this.partner_id = partner_id;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public String getVehicleNo() {
        return vehicle_no;
    }

    public void setVehicleNo(String vehicle_no) {
        this.vehicle_no = vehicle_no;
    }

    public float getCurrentLatitude() {
        return current_latitude;
    }

    public void setCurrentLatitude(float current_latitude) {
        this.current_latitude = current_latitude;
    }

    public float getCurrentLongitude() {
        return current_longitude;
    }

    public void setCurrentLongitude(float current_longitude) {
        this.current_longitude = current_longitude;
    }

    public String getCurrentStatus() {
        return current_status;
    }

    public void setCurrentStatus(String current_status) {
        this.current_status = current_status;
    }

    public boolean isAvailable() {
        return is_available;
    }

    public void setAvailable(boolean is_available) {
        this.is_available = is_available;
    }
}