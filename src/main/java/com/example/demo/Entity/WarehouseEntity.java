package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "warehouse")
public class WarehouseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "warehouse_id")
    @JsonProperty("warehouse_id")
    private Integer warehouse_id;

    @Column(name = "product_id", nullable = false)
    @JsonProperty("product_id")
    private Integer product_id;

    @Column(name = "stock_quantity", nullable = false)
    @JsonProperty("stock_quantity")
    private Integer stock_quantity;

    @Column(name = "warehouse_location")
    @JsonProperty("warehouse_location")
    private Integer warehouse_location;

    @Column(name = "return_stock")
    @JsonProperty("return_stock")
    private Integer return_stock;

    // Default Constructor
    public WarehouseEntity() {
    }

    // Parameterized Constructor
    public WarehouseEntity(Integer warehouse_id, Integer product_id, Integer stock_quantity,
                    Integer warehouse_location, Integer return_stock) {
        this.warehouse_id = warehouse_id;
        this.product_id = product_id;
        this.stock_quantity = stock_quantity;
        this.warehouse_location = warehouse_location;
        this.return_stock = return_stock;
    }

    // Getters and Setters

    public Integer getwarehouse_id() {
        return warehouse_id;
    }

    public void setwarehouse_id(Integer warehouse_id) {
        this.warehouse_id = warehouse_id;
    }

    public Integer getproduct_id() {
        return product_id;
    }

    public void setproduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public Integer getstock_quantity() {
        return stock_quantity;
    }

    public void setstock_quantity(Integer stock_quantity) {
        this.stock_quantity = stock_quantity;
    }

    public Integer getwarehouse_location() {
        return warehouse_location;
    }

    public void setwarehouse_location(Integer warehouse_location) {
        this.warehouse_location = warehouse_location;
    }

    public Integer getreturn_stock() {
        return return_stock;
    }

    public void setreturn_stock(Integer return_stock) {
        this.return_stock = return_stock;
    }

    @Override 
    public String toString() {
        return "WarehouseEntity{"
                + "warehouse_id=" + warehouse_id
                + ", product_id=" + product_id
                + ", stock_quantity=" + stock_quantity
                + ", warehouse_location=" + warehouse_location
                + ", return_stock=" + return_stock
                + '}';
    }
}