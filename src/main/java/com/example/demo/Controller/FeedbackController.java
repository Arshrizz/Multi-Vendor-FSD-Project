package com.example.demo.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.Feedback;
import com.example.demo.Repository.FeedbackRepository;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackRepository feedbackRepository;

    // =====================================================
    // CREATE FEEDBACK
    // POST /api/feedback
    // =====================================================
    @PostMapping
    public ResponseEntity<Feedback> createFeedback(
            @RequestBody Feedback feedback) {

        Feedback savedFeedback = feedbackRepository.save(feedback);

        return new ResponseEntity<>(
                savedFeedback,
                HttpStatus.CREATED
        );
    }

    // =====================================================
    // GET ALL FEEDBACK
    // GET /api/feedback
    // =====================================================
    @GetMapping
    public ResponseEntity<List<Feedback>> getAllFeedback() {

        List<Feedback> feedbackList = feedbackRepository.findAll();

        return ResponseEntity.ok(feedbackList);
    }

    // =====================================================
    // GET FEEDBACK BY ID
    // GET /api/feedback/{id}
    // =====================================================
    @GetMapping("/{id}")
    public ResponseEntity<Feedback> getFeedbackById(
            @PathVariable Long id) {

        Optional<Feedback> feedback =
                feedbackRepository.findById(id);

        return feedback
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );
    }

    // =====================================================
    // GET FEEDBACK BY USER ID
    // GET /api/feedback/user/{userId}
    // =====================================================
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Feedback>> getByUserId(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                feedbackRepository.findByUserId(userId)
        );
    }

    // =====================================================
    // GET FEEDBACK BY ORDER ID
    // GET /api/feedback/order/{orderId}
    // =====================================================
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<Feedback>> getByOrderId(
            @PathVariable Long orderId) {

        return ResponseEntity.ok(
                feedbackRepository.findByOrderId(orderId)
        );
    }

    // =====================================================
    // GET FEEDBACK BY PARTNER ID
    // GET /api/feedback/partner/{partnerId}
    // =====================================================
    @GetMapping("/partner/{partnerId}")
    public ResponseEntity<List<Feedback>> getByPartnerId(
            @PathVariable Long partnerId) {

        return ResponseEntity.ok(
                feedbackRepository.findByPartnerId(partnerId)
        );
    }

    // =====================================================
    // GET FEEDBACK BY RATING
    // GET /api/feedback/rating/{rating}
    // =====================================================
    @GetMapping("/rating/{rating}")
    public ResponseEntity<List<Feedback>> getByRating(
            @PathVariable Integer rating) {

        return ResponseEntity.ok(
                feedbackRepository.findByRating(rating)
        );
    }

    // =====================================================
    // GET FEEDBACK BY CATEGORY
    // GET /api/feedback/category/{category}
    // =====================================================
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Feedback>> getByCategory(
            @PathVariable String category) {

        return ResponseEntity.ok(
                feedbackRepository.findByFeedbackCategory(category)
        );
    }

    // =====================================================
    // UPDATE FEEDBACK
    // PUT /api/feedback/{id}
    // =====================================================
    @PutMapping("/{id}")
    public ResponseEntity<Feedback> updateFeedback(
            @PathVariable Long id,
            @RequestBody Feedback updatedFeedback) {

        return feedbackRepository.findById(id)
                .map(existingFeedback -> {

                    existingFeedback.setUserId(
                            updatedFeedback.getUserId()
                    );

                    existingFeedback.setOrderId(
                            updatedFeedback.getOrderId()
                    );

                    existingFeedback.setPartnerId(
                            updatedFeedback.getPartnerId()
                    );

                    existingFeedback.setRating(
                            updatedFeedback.getRating()
                    );

                    existingFeedback.setComment(
                            updatedFeedback.getComment()
                    );

                    existingFeedback.setFeedbackCategory(
                            updatedFeedback.getFeedbackCategory()
                    );

                    Feedback savedFeedback =
                            feedbackRepository.save(existingFeedback);

                    return ResponseEntity.ok(savedFeedback);
                })
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );
    }

    // =====================================================
// PATCH FEEDBACK
// PATCH /api/feedback/{id}
// =====================================================
@PatchMapping("/{id}")
public ResponseEntity<Feedback> patchFeedback(
        @PathVariable Long id,
        @RequestBody Feedback updatedFeedback) {

    Optional<Feedback> optionalFeedback =
            feedbackRepository.findById(id);

    if (optionalFeedback.isEmpty()) {
        return ResponseEntity.notFound().build();
    }

    Feedback existingFeedback = optionalFeedback.get();

    // Update only the fields provided in the request

    if (updatedFeedback.getUserId() != null) {
        existingFeedback.setUserId(
                updatedFeedback.getUserId()
        );
    }

    if (updatedFeedback.getOrderId() != null) {
        existingFeedback.setOrderId(
                updatedFeedback.getOrderId()
        );
    }

    if (updatedFeedback.getPartnerId() != null) {
        existingFeedback.setPartnerId(
                updatedFeedback.getPartnerId()
        );
    }

    if (updatedFeedback.getRating() != null) {
        existingFeedback.setRating(
                updatedFeedback.getRating()
        );
    }

    if (updatedFeedback.getComment() != null) {
        existingFeedback.setComment(
                updatedFeedback.getComment()
        );
    }

    if (updatedFeedback.getFeedbackCategory() != null) {
        existingFeedback.setFeedbackCategory(
                updatedFeedback.getFeedbackCategory()
        );
    }

    Feedback savedFeedback =
            feedbackRepository.save(existingFeedback);

    return ResponseEntity.ok(savedFeedback);
}

    // =====================================================
    // DELETE FEEDBACK
    // DELETE /api/feedback/{id}
    // =====================================================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeedback(
            @PathVariable Long id) {

        if (!feedbackRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        feedbackRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}