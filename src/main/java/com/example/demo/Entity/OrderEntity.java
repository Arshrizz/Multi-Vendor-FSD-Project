package com.example.demo.Entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "status")
    private String status;

    @Column(name = "total_items")
    private Integer totalItems;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItemEntity> orderItems = new ArrayList<>();

    // Default constructor
    public OrderEntity() {
    }

    public OrderEntity(UserEntity user) {
        this.user = user;
        this.orderDate = LocalDateTime.now();
        this.status = "PLACED";
        this.totalItems = 0;
        this.totalAmount = BigDecimal.ZERO;
    }

    // Adds a line item and keeps the order totals in sync
    public void addItem(OrderItemEntity item) {
        item.setOrder(this);
        orderItems.add(item);
        calculateTotals();
    }

    public void calculateTotals() {
        int items = 0;
        BigDecimal amount = BigDecimal.ZERO;
        for (OrderItemEntity item : orderItems) {
            items += item.getQuantity();
            amount = amount.add(item.getSubtotal());
        }
        this.totalItems = items;
        this.totalAmount = amount;
    }

    // Getters and Setters
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<OrderItemEntity> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemEntity> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public String toString() {
        return "OrderEntity{"
                + "orderId=" + orderId
                + ", userId=" + (user != null ? user.getId() : null)
                + ", orderDate=" + orderDate
                + ", status='" + status + '\''
                + ", totalItems=" + totalItems
                + ", totalAmount=" + totalAmount
                + '}';
    }
}
