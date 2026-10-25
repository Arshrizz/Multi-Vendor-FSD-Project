
package com.example.demo.Controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.example.demo.Entity.FeedbackEntity;
import com.example.demo.Repository.FeedbackRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    private static final Logger logger =
            LoggerFactory.getLogger(FeedbackController.class);

    private final FeedbackRepository feedbackRepository;

    public FeedbackController(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    // Get all feedback
    @Operation(summary = "Get all feedback")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Feedback retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<FeedbackEntity>> getAllFeedback() {
        logger.info("Fetching all feedback");

        List<FeedbackEntity> feedbackList =
                feedbackRepository.findAll();

        return ResponseEntity.ok(feedbackList);
    }

    // Get feedback by ID
    @Operation(summary = "Get feedback by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Feedback found"),
            @ApiResponse(responseCode = "404",
                    description = "Feedback not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getFeedbackById(@PathVariable Long id) {
        logger.info("Fetching feedback with ID: {}", id);

        Optional<FeedbackEntity> feedback =
                feedbackRepository.findById(id);

        if (feedback.isPresent()) {
            return ResponseEntity.ok(feedback.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Feedback not found with ID: " + id);
    }

    // Add feedback
    @Operation(summary = "Add new feedback")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Feedback created successfully"),
            @ApiResponse(responseCode = "400",
                    description = "Invalid feedback data")
    })
    @PostMapping
    public ResponseEntity<?> addFeedback(
            @RequestBody FeedbackEntity feedback) {

        logger.info("Adding new feedback");

        if (feedback == null || feedback.getFeedbackId() == null) {
            return ResponseEntity.badRequest()
                    .body("Feedback ID is required");
        }

        if (feedbackRepository.existsById(feedback.getFeedbackId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Feedback ID already exists");
        }

        FeedbackEntity savedFeedback =
                feedbackRepository.save(feedback);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedFeedback);
    }

    // Update feedback using the ID in the request body
    @Operation(summary = "Update feedback")
    @PutMapping
    public ResponseEntity<?> updateFeedback(
            @RequestBody FeedbackEntity feedback) {

        if (feedback == null || feedback.getFeedbackId() == null) {
            return ResponseEntity.badRequest()
                    .body("Feedback ID is required");
        }

        Long id = feedback.getFeedbackId();

        if (!feedbackRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Feedback not found with ID: " + id);
        }

        FeedbackEntity updatedFeedback =
                feedbackRepository.save(feedback);

        logger.info("Updated feedback with ID: {}", id);

        return ResponseEntity.ok(updatedFeedback);
    }

    // Update feedback using the ID in the URL
    @Operation(summary = "Update feedback by ID")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateFeedbackById(
            @PathVariable Long id,
            @RequestBody FeedbackEntity feedback) {

        Optional<FeedbackEntity> existingFeedback =
                feedbackRepository.findById(id);

        if (existingFeedback.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Feedback not found with ID: " + id);
        }

        feedback.setFeedbackId(id);

        FeedbackEntity updatedFeedback =
                feedbackRepository.save(feedback);

        logger.info("Updated feedback with ID: {}", id);

        return ResponseEntity.ok(updatedFeedback);
    }

    // Partially update feedback
    @Operation(summary = "Partially update feedback")
    @PatchMapping("/{id}")
    public ResponseEntity<?> patchFeedback(
            @PathVariable Long id,
            @RequestBody FeedbackEntity feedback) {

        Optional<FeedbackEntity> optionalFeedback =
                feedbackRepository.findById(id);

        if (optionalFeedback.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Feedback not found with ID: " + id);
        }

        FeedbackEntity existingFeedback = optionalFeedback.get();

        if (feedback.getUserId() != null) {
            existingFeedback.setUserId(feedback.getUserId());
        }

        if (feedback.getOrderId() != null) {
            existingFeedback.setOrderId(feedback.getOrderId());
        }

        if (feedback.getPartnerId() != null) {
            existingFeedback.setPartnerId(feedback.getPartnerId());
        }

        if (feedback.getRating() != null) {
            existingFeedback.setRating(feedback.getRating());
        }

        if (feedback.getComment() != null) {
            existingFeedback.setComment(feedback.getComment());
        }

        if (feedback.getFeedbackCategory() != null) {
            existingFeedback.setFeedbackCategory(
                    feedback.getFeedbackCategory());
        }

        FeedbackEntity updatedFeedback =
                feedbackRepository.save(existingFeedback);

        logger.info("Partially updated feedback with ID: {}", id);

        return ResponseEntity.ok(updatedFeedback);
    }

    // Delete feedback
    @Operation(summary = "Delete feedback")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Feedback deleted successfully"),
            @ApiResponse(responseCode = "404",
                    description = "Feedback not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFeedback(@PathVariable Long id) {
        logger.info("Deleting feedback with ID: {}", id);

        if (!feedbackRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Feedback not found with ID: " + id);
        }

        feedbackRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    // Test endpoint
    @Operation(summary = "Test feedback controller")
    @GetMapping("/test")
    public ResponseEntity<String> testFeedback() {
        return ResponseEntity.ok(
                "Feedback Controller is working successfully!");
    }
}
