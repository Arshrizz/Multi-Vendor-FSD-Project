package com.example.demo.Controller;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.UserEntity;
import com.example.demo.Repository.UserRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class UserController {

    private final UserRepository userRepository;

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // GET ALL USERS
    @Operation(
            summary = "Get all users",
            description = "Retrieve a list of all users in the database."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of users"),
        @ApiResponse(responseCode = "404", description = "No users found"),
        @ApiResponse(responseCode = "500", description = "Database error occurred")
    })
    @GetMapping("/users")
    public List<UserEntity> getAllUsers() {

        log.debug("Fetching all users");

        List<UserEntity> users = userRepository.findAll();

        log.info("End of Get request by -- " + users);

        return users;
    }

    // GET USER BY ID
    @Operation(
            summary = "Get user by ID",
            description = "Retrieve a user by their unique ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved user"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "500", description = "Database error occurred")
    })
    @GetMapping("/users/{id}")
    public Optional<UserEntity> getUserById(
            @PathVariable("id") Long id) {

        log.debug("Fetching user by ID: " + id);

        Optional<UserEntity> user = userRepository.findById(id);

        log.info("End of Get request by -- " + user);

        return user;
    }

    // ADD USER
    @Operation(
            summary = "Add a new user",
            description = "Create a new user record in the database."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "500", description = "Database error occurred")
    })
    @PostMapping("/users")
    public UserEntity addUser(
            @RequestBody UserEntity entity) {

        log.debug("Adding new user: " + entity.getId());

        if (entity.getCreatedAt() == null) {
            entity.setCreatedAt(OffsetDateTime.now());
        }

        if (entity.getStatus() == null) {
            entity.setStatus(true);
        }

        if (entity.getRole() == null) {
            entity.setRole("USER");
        }

        UserEntity savedUser = userRepository.save(entity);

        log.info("End of Post request by -- " + savedUser.getId());

        return savedUser;
    }

    // UPDATE USER
    @Operation(
            summary = "Update an existing user",
            description = "Update the details of an existing user record."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User updated successfully"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "500", description = "Database error occurred")
    })
    @PutMapping("/users")
    public UserEntity updateUser(
            @RequestBody UserEntity entity) {

        log.debug("Updating user: " + entity.getId());

        UserEntity updatedUser = userRepository.save(entity);

        log.info("End of Put request by -- " + updatedUser.getId());

        return updatedUser;
    }

    // UPDATE USER BY ID
    @Operation(
            summary = "Update an existing user by ID",
            description = "Update the details of an existing user record by their ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User updated successfully"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "500", description = "Database error occurred")
    })
    @PutMapping("/users/{id}")
    public String updateUserById(
            @PathVariable("id") Long id,
            @RequestBody UserEntity entity) {

        log.debug("Updating user by ID: " + id);

        Optional<UserEntity> tempUser = userRepository.findById(id);

        if (tempUser.isPresent()) {

            UserEntity existing = tempUser.get();

            existing.setId(id);
            existing.setName(entity.getName());
            existing.setEmail(entity.getEmail());
            existing.setPhone(entity.getPhone());
            existing.setPasswordHash(entity.getPasswordHash());
            if (entity.getRole() != null) {
                existing.setRole(entity.getRole());
            }
            if (entity.getStatus() != null) {
                existing.setStatus(entity.getStatus());
            }
            if (entity.getCreatedAt() != null) {
                existing.setCreatedAt(entity.getCreatedAt());
            }
            existing.setBday(entity.getBday());

            userRepository.save(existing);

            log.info("User " + id + " updated successfully");

            return "User " + id + " Data Updated Successfully";

        } else {

            entity.setId(id);
            if (entity.getCreatedAt() == null) {
                entity.setCreatedAt(OffsetDateTime.now());
            }
            if (entity.getStatus() == null) {
                entity.setStatus(true);
            }
            if (entity.getRole() == null) {
                entity.setRole("USER");
            }
            userRepository.save(entity);

            log.info("New user " + id + " created");

            return "User " + id + " was not exist. So new user created";
        }
    }

    // PATCH USER BY ID
    @Operation(
            summary = "Partially update an existing user by ID",
            description = "Update specific fields of an existing user record."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User updated successfully"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "500", description = "Database error occurred")
    })
    @PatchMapping("/users/{id}")
    public String updateUserByIdPatch(
            @PathVariable("id") Long id,
            @RequestBody UserEntity entity) {

        log.debug("Partially updating user by ID: " + id);

        Optional<UserEntity> tempUser = userRepository.findById(id);

        if (tempUser.isPresent()) {

            UserEntity existingUser = tempUser.get();

            if (entity.getName() != null) {
                existingUser.setName(entity.getName());
            }

            if (entity.getEmail() != null) {
                existingUser.setEmail(entity.getEmail());
            }

            if (entity.getPhone() != null) {
                existingUser.setPhone(entity.getPhone());
            }

            if (entity.getPasswordHash() != null) {
                existingUser.setPasswordHash(entity.getPasswordHash());
            }

            if (entity.getRole() != null) {
                existingUser.setRole(entity.getRole());
            }

            if (entity.getStatus() != null) {
                existingUser.setStatus(entity.getStatus());
            }

            if (entity.getCreatedAt() != null) {
                existingUser.setCreatedAt(entity.getCreatedAt());
            }

            if (entity.getBday() != null) {
                existingUser.setBday(entity.getBday());
            }

            userRepository.save(existingUser);

            log.info("User " + id + " partially updated");

            return "User " + id + " Data Updated Successfully";

        } else {

            return "User " + id + " does not exist";
        }
    }

    // DELETE USER
    @Operation(
            summary = "Delete a user by ID",
            description = "Remove a user record from the database by their ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User deleted successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/users/{id}")
    public String removeUserById(
            @PathVariable("id") Long id) {

        log.debug("Deleting user by ID: " + id);

        Optional<UserEntity> user = userRepository.findById(id);

        if (user.isPresent()) {

            userRepository.deleteById(id);

            log.info("User " + id + " deleted");

            return "User with ID " + id + " has been deleted.";

        } else {

            return "User with ID " + id + " was not found.";
        }
    }

    // TEST ENDPOINT
    @Operation(
            summary = "Test endpoint",
            description = "A simple test endpoint to verify the API is working."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Test successful")
    })
    @GetMapping("/test")
    public String test() {

        return "User API is working successfully!";
    }
}