package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vendor")
public class VendorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vendor_id")
    private Integer vendorId;

    @Column(name = "user_id")
    private Integer userId;

    //Many-to-Many relation with warehouses
    @ManyToMany
    @JoinTable(
        name = "vendor_warehouse",
        joinColumns = @JoinColumn(name = "vendor_id"),
        inverseJoinColumns = @JoinColumn(name = "warehouse_id"),
        foreignKey = @ForeignKey(name = "fk_vw_vendor"),
        inverseForeignKey = @ForeignKey(name = "fk_vw_warehouse")
    )
    @JsonIgnore
    private Set<WarehouseEntity> warehouses = new HashSet<>();

    @Column(name = "assignment_id")
    private Integer assignmentId;

    @Column(name = "store_name")
    private String storeName;

    // Getters and Setters

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Set<WarehouseEntity> getWarehouses() {
        return warehouses;
    }

    public void setWarehouses(Set<WarehouseEntity> warehouses) {
        this.warehouses = warehouses;
    }

    public Integer getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Integer assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}