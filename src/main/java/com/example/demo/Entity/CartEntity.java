package com.example.demo.Entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart")
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long cartId;

    // One user has one active cart
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    // Cart has payment_id, so Cart is the owning side (see PaymentEntity.cart)
    @OneToOne
    @JoinColumn(name = "payment_id", referencedColumnName = "payment_id")
    private PaymentEntity payment;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItemEntity> cartItems = new ArrayList<>();

    // Default constructor
    public CartEntity() {
    }

    public CartEntity(UserEntity user) {
        this.user = user;
    }

    // Adds a product to the cart; if it is already present the quantity is increased
    public void addItem(ProductEntity product, int quantity) {
        for (CartItemEntity item : cartItems) {
            if (item.getProduct().getProductId().equals(product.getProductId())) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        cartItems.add(new CartItemEntity(this, product, quantity));
    }

    public void removeItem(Long productId) {
        cartItems.removeIf(item -> item.getProduct().getProductId().equals(productId));
    }

    public void clearItems() {
        cartItems.clear();
    }

    public int getTotalItems() {
        int total = 0;
        for (CartItemEntity item : cartItems) {
            total += item.getQuantity();
        }
        return total;
    }

    public BigDecimal getTotalAmount() {
        BigDecimal total = BigDecimal.ZERO;
        for (CartItemEntity item : cartItems) {
            total = total.add(item.getSubtotal());
        }
        return total;
    }

    // Getters and Setters
    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public PaymentEntity getPayment() {
        return payment;
    }

    public void setPayment(PaymentEntity payment) {
        this.payment = payment;
    }

    public List<CartItemEntity> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemEntity> cartItems) {
        this.cartItems = cartItems;
    }

    @Override
    public String toString() {
        return "CartEntity{"
                + "cartId=" + cartId
                + ", userId=" + (user != null ? user.getId() : null)
                + ", paymentId=" + (payment != null ? payment.getPaymentId() : null)
                + ", totalItems=" + getTotalItems()
                + '}';
    }
}
