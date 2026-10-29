package com.example.demo.Entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cart_item")
public class CartItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long cartItemId;

    @ManyToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "cart_id")
    private CartEntity cart;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private ProductEntity product;

    @Column(name = "quantity")
    private Integer quantity;

    // Default constructor
    public CartItemEntity() {
    }

    public CartItemEntity(CartEntity cart, ProductEntity product, Integer quantity) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }

    // Price is read live from the product (cart prices are not frozen, unlike order items)
    public BigDecimal getSubtotal() {
        if (product == null || product.getPrice() == null || quantity == null) {
            return BigDecimal.ZERO;
        }
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

    // Getters and Setters
    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public CartEntity getCart() {
        return cart;
    }

    public void setCart(CartEntity cart) {
        this.cart = cart;
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

    @Override
    public String toString() {
        return "CartItemEntity{"
                + "cartItemId=" + cartItemId
                + ", cartId=" + (cart != null ? cart.getCartId() : null)
                + ", productId=" + (product != null ? product.getProductId() : null)
                + ", quantity=" + quantity
                + '}';
    }
}
