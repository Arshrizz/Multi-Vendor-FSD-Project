package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "warehouse", indexes = {
    @Index(name = "idx_warehouse_location", columnList = "warehouse_location")
})
public class WarehouseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "warehouse_id")
    @JsonProperty("warehouse_id")
    private Integer warehouse_id;

    @Column(name = "stock_quantity", nullable = false)
    @JsonProperty("stock_quantity")
    private Integer stock_quantity;

    @Column(name = "return_stock")
    @JsonProperty("return_stock")
    private Integer return_stock;

    // One-to-One relation with address
    @OneToOne
    @JoinColumn(name = "warehouse_location", unique = true,
        foreignKey = @ForeignKey(name = "fk_warehouse_address"))
    private AddressEntity address;

    // Many-to-Many relation with products
    @ManyToMany
    @JoinTable(
        name = "warehouse_product",
        joinColumns = @JoinColumn(name = "warehouse_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id"),
        foreignKey = @ForeignKey(name = "fk_wp_warehouse"),
        inverseForeignKey = @ForeignKey(name = "fk_wp_product")
    )
    @JsonIgnore
    private Set<ProductEntity> products = new HashSet<>();

    // Many-to-Many relation with vendors
    @ManyToMany(mappedBy = "warehouses")
    @JsonIgnore
    private Set<VendorEntity> vendors = new HashSet<>();

    // Default Constructor
    public WarehouseEntity() {
    }

    // Parameterized Constructors
    public WarehouseEntity(Integer warehouse_id, Integer stock_quantity, Integer return_stock, AddressEntity address) {
        this.warehouse_id = warehouse_id;
        this.stock_quantity = stock_quantity;
        this.return_stock = return_stock;
        this.address = address;
    }

    public WarehouseEntity(Integer warehouse_id, AddressEntity address) {
        this.warehouse_id = warehouse_id;
        this.address = address;
    }

    // Getters and Setters

    public Integer getwarehouse_id() {
        return warehouse_id;
    }

    public void setwarehouse_id(Integer warehouse_id) {
        this.warehouse_id = warehouse_id;
    }

    public Integer getstock_quantity() {
        return stock_quantity;
    }

    public void setstock_quantity(Integer stock_quantity) {
        this.stock_quantity = stock_quantity;
    }

    public Integer getreturn_stock() {
        return return_stock;
    }

    public void setreturn_stock(Integer return_stock) {
        this.return_stock = return_stock;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    public Set<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductEntity> products) {
        this.products = products;
    }

    public Set<VendorEntity> getVendors() {
        return vendors;
    }

    public void setVendors(Set<VendorEntity> vendors) {
        this.vendors = vendors;
    }

    @Override 
    public String toString() {
        return "WarehouseEntity{"
                + "warehouse_id=" + warehouse_id
                + ", stock_quantity=" + stock_quantity
                + ", return_stock=" + return_stock
                + ", warehouse_location=" + (address != null ? address.getAddress_id() : null)
                + '}';
    }
}