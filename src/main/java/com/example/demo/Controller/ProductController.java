package com.example.demo.Controller;

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

import com.example.demo.Entity.ProductEntity;
import com.example.demo.Repository.ProductRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class ProductController {

    private final ProductRepository productRepository;

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // GET ALL PRODUCTS
    @Operation(
            summary = "Get all products",
            description = "Retrieve a list of all products in the database."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of products"),
        @ApiResponse(responseCode = "404", description = "No products found"),
        @ApiResponse(responseCode = "500", description = "Database error occurred")
    })
    @GetMapping("/products")
    public List<ProductEntity> getAllProducts() {

        log.debug("Fetching all products");

        List<ProductEntity> products = productRepository.findAll();

        log.info("End of Get request by -- " + products);

        return products;
    }

    // GET PRODUCT BY ID
    @Operation(
            summary = "Get product by ID",
            description = "Retrieve a product by its unique ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved product"),
        @ApiResponse(responseCode = "404", description = "Product not found"),
        @ApiResponse(responseCode = "500", description = "Database error occurred")
    })
    @GetMapping("/products/{id}")
    public Optional<ProductEntity> getProductById(
            @PathVariable("id") Long id) {

        log.debug("Fetching product by ID: " + id);

        Optional<ProductEntity> product = productRepository.findById(id);

        log.info("End of Get request by -- " + product);

        return product;
    }

    // ADD PRODUCT
    @Operation(
            summary = "Add a new product",
            description = "Create a new product record in the database."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Product created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "500", description = "Database error occurred")
    })
    @PostMapping("/products")
    public ProductEntity addProduct(
            @RequestBody ProductEntity entity) {

        log.debug("Adding new product: " + entity.getProductId());

        ProductEntity savedProduct = productRepository.save(entity);

        log.info("End of Post request by -- " + savedProduct.getProductId());

        return savedProduct;
    }

    // UPDATE PRODUCT
    @Operation(
            summary = "Update an existing product",
            description = "Update the details of an existing product record."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product updated successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "500", description = "Database error occurred")
    })
    @PutMapping("/products")
    public ProductEntity updateProduct(
            @RequestBody ProductEntity entity) {

        log.debug("Updating product: " + entity.getProductId());

        ProductEntity updatedProduct = productRepository.save(entity);

        log.info("End of Put request by -- " + updatedProduct.getProductId());

        return updatedProduct;
    }

    // UPDATE PRODUCT BY ID
    @Operation(
            summary = "Update an existing product by ID",
            description = "Update the details of an existing product record by its ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product updated successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "500", description = "Database error occurred")
    })
    @PutMapping("/products/{id}")
    public String updateProductById(
            @PathVariable("id") Long id,
            @RequestBody ProductEntity entity) {

        log.debug("Updating product by ID: " + id);

        Optional<ProductEntity> tempProduct = productRepository.findById(id);

        if (tempProduct.isPresent()) {

            ProductEntity existing = tempProduct.get();

            existing.setProductId(id);
            existing.setCategory(entity.getCategory());
            existing.setVendorId(entity.getVendorId());
            existing.setProductName(entity.getProductName());
            existing.setBrand(entity.getBrand());
            existing.setPrice(entity.getPrice());
            existing.setStatus(entity.getStatus());

            productRepository.save(existing);

            log.info("Product " + id + " updated successfully");

            return "Product " + id + " Data Updated Successfully";

        } else {

            entity.setProductId(id);
            productRepository.save(entity);

            log.info("New product " + id + " created");

            return "Product " + id + " was not exist. So new product created";
        }
    }

    // PATCH PRODUCT BY ID
    @Operation(
            summary = "Partially update an existing product by ID",
            description = "Update specific fields of an existing product record."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product updated successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "500", description = "Database error occurred")
    })
    @PatchMapping("/products/{id}")
    public String updateProductByIdPatch(
            @PathVariable("id") Long id,
            @RequestBody ProductEntity entity) {

        log.debug("Partially updating product by ID: " + id);

        Optional<ProductEntity> tempProduct = productRepository.findById(id);

        if (tempProduct.isPresent()) {

            ProductEntity existingProduct = tempProduct.get();

            if (entity.getCategory() != null) {
                existingProduct.setCategory(entity.getCategory());
            }

            if (entity.getVendorId() != null) {
                existingProduct.setVendorId(entity.getVendorId());
            }

            if (entity.getProductName() != null) {
                existingProduct.setProductName(entity.getProductName());
            }

            if (entity.getBrand() != null) {
                existingProduct.setBrand(entity.getBrand());
            }

            if (entity.getPrice() != null) {
                existingProduct.setPrice(entity.getPrice());
            }

            if (entity.getStatus() != null) {
                existingProduct.setStatus(entity.getStatus());
            }

            productRepository.save(existingProduct);

            log.info("Product " + id + " partially updated");

            return "Product " + id + " Data Updated Successfully";

        } else {

            return "Product " + id + " does not exist";
        }
    }

    // DELETE PRODUCT
    @Operation(
            summary = "Delete a product by ID",
            description = "Remove a product record from the database by its ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @DeleteMapping("/products/{id}")
    public String removeProductById(
            @PathVariable("id") Long id) {

        log.debug("Deleting product by ID: " + id);

        Optional<ProductEntity> product = productRepository.findById(id);

        if (product.isPresent()) {

            productRepository.deleteById(id);

            log.info("Product " + id + " deleted");

            return "Product with ID " + id + " has been deleted.";

        } else {

            return "Product with ID " + id + " was not found.";
        }
    }

    // TEST ENDPOINT
    @Operation(
            summary = "Test endpoint",
            description = "A simple test endpoint to verify the Product API is working."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Test successful")
    })
    @GetMapping("/products/test")
    public String test() {

        return "Product API is working successfully!";
    }
}
