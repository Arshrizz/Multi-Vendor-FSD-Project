package com.example.demo.Controller;

import com.example.demo.Entity.PaymentEntity;
import com.example.demo.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;

    // Get all payments
    @GetMapping
    public List<PaymentEntity> getAllPayments() {
        return paymentRepository.findAll();
    }

    // Get payment by ID
    @GetMapping("/{id}")
    public Optional<PaymentEntity> getPaymentById(@PathVariable Long id) {
        return paymentRepository.findById(id);
    }

    // Create new payment
    @PostMapping
    public PaymentEntity createPayment(@RequestBody PaymentEntity payment) {
        return paymentRepository.save(payment);
    }

    @PatchMapping("/{id}")
    public PaymentEntity patchPayment(
            @PathVariable Long id,
            @RequestBody PaymentEntity payment) {

        PaymentEntity existingPayment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        if (payment.getVendorId() != null) {
            existingPayment.setVendorId(payment.getVendorId());
        }

        if (payment.getOrderId() != null) {
            existingPayment.setOrderId(payment.getOrderId());
        }

        if (payment.getPaymentMethod() != null) {
            existingPayment.setPaymentMethod(payment.getPaymentMethod());
        }

        if (payment.getPaymentDate() != null) {
            existingPayment.setPaymentDate(payment.getPaymentDate());
        }

        if (payment.getPaymentTime() != null) {
            existingPayment.setPaymentTime(payment.getPaymentTime());
        }

        return paymentRepository.save(existingPayment);
    }

    // Update payment
    @PutMapping("/{id}")
    public PaymentEntity updatePayment(
            @PathVariable Long id,
            @RequestBody PaymentEntity payment) {

        payment.setPaymentId(id);
        return paymentRepository.save(payment);
    }

    // Delete payment
    @DeleteMapping("/{id}")
    public String deletePayment(@PathVariable Long id) {

        paymentRepository.deleteById(id);

        return "Payment deleted successfully";
    }
}