package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private VendorEntity vendor;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "brand")
    private String brand;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "status")
    private String status;

    // Many-to-Many relation with warehouses
    @ManyToMany(mappedBy = "products")
    @JsonIgnore
    private Set<WarehouseEntity> warehouses = new HashSet<>();

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public VendorEntity getVendor() {
        return vendor;
    }

    public void setVendor(VendorEntity vendor) {
        this.vendor = vendor;
    }

    @JsonProperty("vendorId")
    public Integer getVendorId() {
        return vendor != null ? vendor.getVendorId() : null;
    }

    @JsonProperty("vendorId")
    public void setVendorId(Integer vendorId) {
        if (vendorId != null) {
            if (this.vendor == null) {
                this.vendor = new VendorEntity();
            }
            this.vendor.setVendorId(vendorId);
        } else {
            this.vendor = null;
        }
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<WarehouseEntity> getWarehouses() {
        return warehouses;
    }

    public void setWarehouses(Set<WarehouseEntity> warehouses) {
        this.warehouses = warehouses;
    }
}
