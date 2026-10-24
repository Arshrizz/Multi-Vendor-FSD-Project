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

import com.example.demo.Entity.CategoryEntity;
import com.example.demo.Repository.CategoryRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class CategoryController {

    private final CategoryRepository categoryRepository;

    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // GET ALL CATEGORIES
    @Operation(
            summary = "Get all categories",
            description = "Retrieve a list of all categories in the database."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of categories"),
        @ApiResponse(responseCode = "404", description = "No categories found"),
        @ApiResponse(responseCode = "500", description = "Database error occurred")
    })
    @GetMapping("/categories")
    public List<CategoryEntity> getAllCategories() {

        log.debug("Fetching all categories");

        List<CategoryEntity> categories = categoryRepository.findAll();

        log.info("End of Get request by -- " + categories);

        return categories;
    }

    // GET CATEGORY BY ID
    @Operation(
            summary = "Get category by ID",
            description = "Retrieve a category by its unique ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved category"),
        @ApiResponse(responseCode = "404", description = "Category not found"),
        @ApiResponse(responseCode = "500", description = "Database error occurred")
    })
    @GetMapping("/categories/{id}")
    public Optional<CategoryEntity> getCategoryById(
            @PathVariable("id") Long id) {

        log.debug("Fetching category by ID: " + id);

        Optional<CategoryEntity> category = categoryRepository.findById(id);

        log.info("End of Get request by -- " + category);

        return category;
    }

    // ADD CATEGORY
    @Operation(
            summary = "Add a new category",
            description = "Create a new category record in the database."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Category created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "500", description = "Database error occurred")
    })
    @PostMapping("/categories")
    public CategoryEntity addCategory(
            @RequestBody CategoryEntity entity) {

        log.debug("Adding new category: " + entity.getCategoryId());

        CategoryEntity savedCategory = categoryRepository.save(entity);

        log.info("End of Post request by -- " + savedCategory.getCategoryId());

        return savedCategory;
    }

    // UPDATE CATEGORY
    @Operation(
            summary = "Update an existing category",
            description = "Update the details of an existing category record."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Category updated successfully"),
        @ApiResponse(responseCode = "404", description = "Category not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "500", description = "Database error occurred")
    })
    @PutMapping("/categories")
    public CategoryEntity updateCategory(
            @RequestBody CategoryEntity entity) {

        log.debug("Updating category: " + entity.getCategoryId());

        CategoryEntity updatedCategory = categoryRepository.save(entity);

        log.info("End of Put request by -- " + updatedCategory.getCategoryId());

        return updatedCategory;
    }

    // UPDATE CATEGORY BY ID
    @Operation(
            summary = "Update an existing category by ID",
            description = "Update the details of an existing category record by its ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Category updated successfully"),
        @ApiResponse(responseCode = "404", description = "Category not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "500", description = "Database error occurred")
    })
    @PutMapping("/categories/{id}")
    public String updateCategoryById(
            @PathVariable("id") Long id,
            @RequestBody CategoryEntity entity) {

        log.debug("Updating category by ID: " + id);

        Optional<CategoryEntity> tempCategory = categoryRepository.findById(id);

        if (tempCategory.isPresent()) {

            CategoryEntity existing = tempCategory.get();

            existing.setCategoryId(id);
            existing.setCategoryName(entity.getCategoryName());
            existing.setDescription(entity.getDescription());
            if (entity.getProducts() != null) {
                existing.setProducts(entity.getProducts());
            }

            categoryRepository.save(existing);

            log.info("Category " + id + " updated successfully");

            return "Category " + id + " Data Updated Successfully";

        } else {

            entity.setCategoryId(id);
            categoryRepository.save(entity);

            log.info("New category " + id + " created");

            return "Category " + id + " was not exist. So new category created";
        }
    }

    // PATCH CATEGORY BY ID
    @Operation(
            summary = "Partially update an existing category by ID",
            description = "Update specific fields of an existing category record."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Category updated successfully"),
        @ApiResponse(responseCode = "404", description = "Category not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "500", description = "Database error occurred")
    })
    @PatchMapping("/categories/{id}")
    public String updateCategoryByIdPatch(
            @PathVariable("id") Long id,
            @RequestBody CategoryEntity entity) {

        log.debug("Partially updating category by ID: " + id);

        Optional<CategoryEntity> tempCategory = categoryRepository.findById(id);

        if (tempCategory.isPresent()) {

            CategoryEntity existingCategory = tempCategory.get();

            if (entity.getCategoryName() != null) {
                existingCategory.setCategoryName(entity.getCategoryName());
            }

            if (entity.getDescription() != null) {
                existingCategory.setDescription(entity.getDescription());
            }

            if (entity.getProducts() != null) {
                existingCategory.setProducts(entity.getProducts());
            }

            categoryRepository.save(existingCategory);

            log.info("Category " + id + " partially updated");

            return "Category " + id + " Data Updated Successfully";

        } else {

            return "Category " + id + " does not exist";
        }
    }

    // DELETE CATEGORY
    @Operation(
            summary = "Delete a category by ID",
            description = "Remove a category record from the database by its ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Category deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @DeleteMapping("/categories/{id}")
    public String removeCategoryById(
            @PathVariable("id") Long id) {

        log.debug("Deleting category by ID: " + id);

        Optional<CategoryEntity> category = categoryRepository.findById(id);

        if (category.isPresent()) {

            categoryRepository.deleteById(id);

            log.info("Category " + id + " deleted");

            return "Category with ID " + id + " has been deleted.";

        } else {

            return "Category with ID " + id + " was not found.";
        }
    }

    // TEST ENDPOINT
    @Operation(
            summary = "Test endpoint",
            description = "A simple test endpoint to verify the Category API is working."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Test successful")
    })
    @GetMapping("/categories/test")
    public String test() {

        return "Category API is working successfully!";
    }
}
