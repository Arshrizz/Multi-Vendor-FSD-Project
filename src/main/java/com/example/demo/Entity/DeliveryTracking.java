package com.example.demo.Entity;

import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DeliveryTracking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int assignment_id;

    private int order_id;
    private String assignment_status;
    private String pickup_vendor;
    private String pickup_user;
    private LocalTime ETA;

    // Default constructor
    public DeliveryTracking() {
    }

    // Parameterized constructor
    public DeliveryTracking(int order_id, String assignment_status,
                            String pickup_vendor, String pickup_user,
                            LocalTime ETA) {

        this.order_id = order_id;
        this.assignment_status = assignment_status;
        this.pickup_vendor = pickup_vendor;
        this.pickup_user = pickup_user;
        this.ETA = ETA;
    }

    // Getters and Setters

    public int getAssignmentId() {
        return assignment_id;
    }

    public void setAssignmentId(int assignment_id) {
        this.assignment_id = assignment_id;
    }

    public int getOrderId() {
        return order_id;
    }

    public void setOrderId(int order_id) {
        this.order_id = order_id;
    }

    public String getAssignmentStatus() {
        return assignment_status;
    }

    public void setAssignmentStatus(String assignment_status) {
        this.assignment_status = assignment_status;
    }

    public String getPickupVendor() {
        return pickup_vendor;
    }

    public void setPickupVendor(String pickup_vendor) {
        this.pickup_vendor = pickup_vendor;
    }

    public String getPickupUser() {
        return pickup_user;
    }

    public void setPickupUser(String pickup_user) {
        this.pickup_user = pickup_user;
    }

    public LocalTime getETA() {
        return ETA;
    }

    public void setETA(LocalTime ETA) {
        this.ETA = ETA;
    }
}