package com.example.demo.Entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_item")
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long orderItemId;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private ProductEntity product;

    @Column(name = "quantity")
    private Integer quantity;

    // Price at the time of purchase, so later product price changes don't alter past orders
    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @Column(name = "subtotal")
    private BigDecimal subtotal;

    // Default constructor
    public OrderItemEntity() {
    }

    // Snapshots the product's current price into the line item
    public OrderItemEntity(OrderEntity order, ProductEntity product, Integer quantity) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = product.getPrice();
        calculateSubtotal();
    }

    public void calculateSubtotal() {
        if (unitPrice == null || quantity == null) {
            this.subtotal = BigDecimal.ZERO;
        } else {
            this.subtotal = unitPrice.multiply(BigDecimal.valueOf(quantity));
        }
    }

    // Getters and Setters
    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public String toString() {
        return "OrderItemEntity{"
                + "orderItemId=" + orderItemId
                + ", orderId=" + (order != null ? order.getOrderId() : null)
                + ", productId=" + (product != null ? product.getProductId() : null)
                + ", quantity=" + quantity
                + ", unitPrice=" + unitPrice
                + ", subtotal=" + subtotal
                + '}';
    }
}
