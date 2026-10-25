package com.example.demo.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.FeedbackEntity;
import com.example.demo.Repository.FeedbackRepository;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackRepository feedbackRepository;

    // Create feedback
    @PostMapping
    public ResponseEntity<FeedbackEntity> createFeedback(
            @RequestBody FeedbackEntity feedback) {

        FeedbackEntity savedFeedback =
                feedbackRepository.save(feedback);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedFeedback);
    }

    // Get all feedback
    @GetMapping
    public ResponseEntity<List<FeedbackEntity>> getAllFeedback() {

        return ResponseEntity.ok(feedbackRepository.findAll());
    }

    // Get feedback by ID
    @GetMapping("/{id}")
    public ResponseEntity<FeedbackEntity> getFeedbackById(
            @PathVariable Long id) {

        Optional<FeedbackEntity> feedback =
                feedbackRepository.findById(id);

        if (feedback.isPresent()) {
            return ResponseEntity.ok(feedback.get());
        }

        return ResponseEntity.notFound().build();
    }

    // Update feedback
    @PutMapping("/{id}")
    public ResponseEntity<FeedbackEntity> updateFeedback(
            @PathVariable Long id,
            @RequestBody FeedbackEntity updatedFeedback) {

        Optional<FeedbackEntity> optionalFeedback =
                feedbackRepository.findById(id);

        if (optionalFeedback.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        FeedbackEntity existingFeedback = optionalFeedback.get();

        existingFeedback.setUserId(updatedFeedback.getUserId());
        existingFeedback.setOrderId(updatedFeedback.getOrderId());
        existingFeedback.setPartnerId(updatedFeedback.getPartnerId());
        existingFeedback.setRating(updatedFeedback.getRating());
        existingFeedback.setComment(updatedFeedback.getComment());
        existingFeedback.setFeedbackCategory(
                updatedFeedback.getFeedbackCategory());

        FeedbackEntity savedFeedback =
                feedbackRepository.save(existingFeedback);

        return ResponseEntity.ok(savedFeedback);
    }

    // Delete feedback
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
