package com.example.demo.Controller;

import com.example.demo.Entity.OrderEntity;
import com.example.demo.Entity.OrderItemEntity;
import com.example.demo.Repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // Response shapes (entities are not returned directly to avoid circular JSON and leaking user data)
    public record OrderItemResponse(Long orderItemId, Long productId, String productName,
                                    Integer quantity, BigDecimal unitPrice, BigDecimal subtotal) {
        static OrderItemResponse from(OrderItemEntity i) {
            return new OrderItemResponse(i.getOrderItemId(),
                    i.getProduct().getProductId(), i.getProduct().getProductName(),
                    i.getQuantity(), i.getUnitPrice(), i.getSubtotal());
        }
    }

    public record OrderResponse(Long orderId, Long userId, LocalDateTime orderDate, String status,
                                Integer totalItems, BigDecimal totalAmount, List<OrderItemResponse> items) {
        public static OrderResponse from(OrderEntity o) {
            return new OrderResponse(o.getOrderId(), o.getUser().getId(), o.getOrderDate(), o.getStatus(),
                    o.getTotalItems(), o.getTotalAmount(),
                    o.getOrderItems().stream().map(OrderItemResponse::from).toList());
        }
    }

    @GetMapping("/{orderId}")
    @Transactional(readOnly = true)
    public OrderResponse getOrder(@PathVariable Long orderId) {
        return OrderResponse.from(findOrder(orderId));
    }

    @GetMapping("/user/{userId}")
    @Transactional(readOnly = true)
    public List<OrderResponse> getOrdersByUser(@PathVariable Integer userId) {
        return orderRepository.findByUser_IdOrderByOrderDateDesc(userId)
                .stream().map(OrderResponse::from).toList();
    }

    @GetMapping
    @Transactional(readOnly = true)
    public List<OrderResponse> getAllOrders(@RequestParam(required = false) String status) {
        List<OrderEntity> orders = (status == null) ? orderRepository.findAll()
                : orderRepository.findByStatus(status.toUpperCase());
        return orders.stream().map(OrderResponse::from).toList();
    }

    @PutMapping("/{orderId}/status")
    @Transactional
    public OrderResponse updateStatus(@PathVariable Long orderId, @RequestParam String status) {
        OrderEntity order = findOrder(orderId);
        String newStatus = status.toUpperCase();
        if ("CANCELLED".equals(order.getStatus()) || "DELIVERED".equals(order.getStatus())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Order is already " + order.getStatus() + " and cannot be changed");
        }
        order.setStatus(newStatus);
        return OrderResponse.from(orderRepository.save(order));
    }

    @PutMapping("/{orderId}/cancel")
    @Transactional
    public OrderResponse cancelOrder(@PathVariable Long orderId) {
        OrderEntity order = findOrder(orderId);
        if (!"PLACED".equals(order.getStatus()) && !"CONFIRMED".equals(order.getStatus())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Order cannot be cancelled once it is " + order.getStatus());
        }
        order.setStatus("CANCELLED");
        return OrderResponse.from(orderRepository.save(order));
    }

    private OrderEntity findOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found: " + orderId));
    }
}
