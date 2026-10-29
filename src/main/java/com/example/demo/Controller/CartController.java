package com.example.demo.Controller;

import com.example.demo.Controller.OrderController.OrderResponse;
import com.example.demo.Entity.*;
import com.example.demo.Repository.CartRepository;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public CartController(CartRepository cartRepository, ProductRepository productRepository,
                          UserRepository userRepository, OrderRepository orderRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    // Request / response shapes
    public record CartItemRequest(Long productId, Integer quantity) {
    }

    public record QuantityRequest(Integer quantity) {
    }

    public record CartItemResponse(Long cartItemId, Long productId, String productName,
                                   BigDecimal unitPrice, Integer quantity, BigDecimal subtotal) {
        static CartItemResponse from(CartItemEntity i) {
            return new CartItemResponse(i.getCartItemId(), i.getProduct().getProductId(),
                    i.getProduct().getProductName(), i.getProduct().getPrice(),
                    i.getQuantity(), i.getSubtotal());
        }
    }

    public record CartResponse(Long cartId, Long userId, Integer totalItems, BigDecimal totalAmount,
                               List<CartItemResponse> items) {
        static CartResponse from(CartEntity c) {
            return new CartResponse(c.getCartId(), c.getUser().getId(), c.getTotalItems(), c.getTotalAmount(),
                    c.getCartItems().stream().map(CartItemResponse::from).toList());
        }
    }

    @GetMapping("/user/{userId}")
    @Transactional
    public CartResponse getCart(@PathVariable Long userId) {
        return CartResponse.from(getOrCreateCart(userId));
    }

    @PostMapping("/user/{userId}/items")
    @Transactional
    public CartResponse addItem(@PathVariable Long userId, @RequestBody CartItemRequest request) {
        if (request.productId() == null || request.quantity() == null || request.quantity() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "productId and a quantity > 0 are required");
        }
        ProductEntity product = productRepository.findById(request.productId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Product not found: " + request.productId()));
        CartEntity cart = getOrCreateCart(userId);
        cart.addItem(product, request.quantity());
        return CartResponse.from(cartRepository.save(cart));
    }

    @PutMapping("/user/{userId}/items/{productId}")
    @Transactional
    public CartResponse updateQuantity(@PathVariable Long userId, @PathVariable Long productId,
                                       @RequestBody QuantityRequest request) {
        if (request.quantity() == null || request.quantity() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "quantity must be 0 or more");
        }
        CartEntity cart = getOrCreateCart(userId);
        CartItemEntity item = cart.getCartItems().stream()
                .filter(i -> i.getProduct().getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Product " + productId + " is not in the cart"));
        if (request.quantity() == 0) {
            cart.removeItem(productId);
        } else {
            item.setQuantity(request.quantity());
        }
        return CartResponse.from(cartRepository.save(cart));
    }

    @DeleteMapping("/user/{userId}/items/{productId}")
    @Transactional
    public CartResponse removeItem(@PathVariable Long userId, @PathVariable Long productId) {
        CartEntity cart = getOrCreateCart(userId);
        cart.removeItem(productId);
        return CartResponse.from(cartRepository.save(cart));
    }

    @DeleteMapping("/user/{userId}")
    @Transactional
    public CartResponse clearCart(@PathVariable Long userId) {
        CartEntity cart = getOrCreateCart(userId);
        cart.clearItems();
        return CartResponse.from(cartRepository.save(cart));
    }

    // Turns the cart into an order (prices are frozen into order items), then empties the cart
    @PostMapping("/user/{userId}/checkout")
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse checkout(@PathVariable Long userId) {
        CartEntity cart = getOrCreateCart(userId);
        if (cart.getCartItems().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cart is empty");
        }
        OrderEntity order = new OrderEntity(cart.getUser());
        for (CartItemEntity item : cart.getCartItems()) {
            order.addItem(new OrderItemEntity(order, item.getProduct(), item.getQuantity()));
        }
        OrderEntity saved = orderRepository.save(order);
        cart.clearItems();
        cartRepository.save(cart);
        return OrderResponse.from(saved);
    }

    private CartEntity getOrCreateCart(Long userId) {
        return cartRepository.findByUser_Id(userId).orElseGet(() -> {
            UserEntity user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "User not found: " + userId));
            return cartRepository.save(new CartEntity(user));
        });
    }
}
