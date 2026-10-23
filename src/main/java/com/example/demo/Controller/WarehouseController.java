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

import com.example.demo.Entity.WarehouseEntity;
import com.example.demo.Repository.WarehouseRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class WarehouseController {

    private final WarehouseRepository warehouseRepository;

    private static final Logger log = LoggerFactory.getLogger(WarehouseController.class);

    public WarehouseController(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    // GET ALL WAREHOUSES
    @Operation(
            summary = "Get all warehouses",
            description = "Retrieve a list of all warehouses in the database."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list of warehouses"),
        @ApiResponse(responseCode = "404", description = "No warehouses found"),
        @ApiResponse(responseCode = "500", description = "Database error occurred")
    })
    @GetMapping("/warehouses")
    public List<WarehouseEntity> getAllWarehouses() {

        log.debug("Fetching all warehouses");

        List<WarehouseEntity> warehouses = warehouseRepository.findAll();

        log.info("End of Get request by -- " + warehouses);

        return warehouses;
    }

    // GET WAREHOUSE BY ID
    @Operation(
            summary = "Get warehouse by ID",
            description = "Retrieve a warehouse by its unique ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved warehouse"),
        @ApiResponse(responseCode = "404", description = "Warehouse not found"),
        @ApiResponse(responseCode = "500", description = "Database error occurred")
    })
    @GetMapping("/warehouses/{id}")
    public Optional<WarehouseEntity> getWarehouseById(
            @PathVariable("id") Integer id) {

        log.debug("Fetching warehouse by ID: " + id);

        Optional<WarehouseEntity> warehouse = warehouseRepository.findById(id);

        log.info("End of Get request by -- " + warehouse);

        return warehouse;
    }

    // ADD WAREHOUSE
    @Operation(
            summary = "Add a new warehouse",
            description = "Create a new warehouse record in the database."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Warehouse created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "500", description = "Database error occurred")
    })
    @PostMapping("/warehouses")
    public WarehouseEntity addWarehouse(
            @RequestBody WarehouseEntity entity) {

        log.debug("Adding new warehouse: " + entity.getwarehouse_id());

        WarehouseEntity savedWarehouse = warehouseRepository.save(entity);

        log.info("End of Post request by -- " + savedWarehouse.getwarehouse_id());

        return savedWarehouse;
    }

    // UPDATE WAREHOUSE
    @Operation(
            summary = "Update an existing warehouse",
            description = "Update the details of an existing warehouse record."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Warehouse updated successfully"),
        @ApiResponse(responseCode = "404", description = "Warehouse not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "500", description = "Database error occurred")
    })
    @PutMapping("/warehouses")
    public WarehouseEntity updateWarehouse(
            @RequestBody WarehouseEntity entity) {

        log.debug("Updating warehouse: " + entity.getwarehouse_id());

        WarehouseEntity updatedWarehouse = warehouseRepository.save(entity);

        log.info("End of Put request by -- " + updatedWarehouse.getwarehouse_id());

        return updatedWarehouse;
    }

    // UPDATE WAREHOUSE BY ID
    @Operation(
            summary = "Update an existing warehouse by ID",
            description = "Update the details of an existing warehouse record by its ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Warehouse updated successfully"),
        @ApiResponse(responseCode = "404", description = "Warehouse not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "500", description = "Database error occurred")
    })
    @PutMapping("/warehouses/{id}")
    public String updateWarehouseById(
            @PathVariable("id") Integer id,
            @RequestBody WarehouseEntity entity) {

        log.debug("Updating warehouse by ID: " + id);

        Optional<WarehouseEntity> tempWarehouse = warehouseRepository.findById(id);

        if (tempWarehouse.isPresent()) {

            WarehouseEntity existing = tempWarehouse.get();

            existing.setwarehouse_id(id);
            existing.setproduct_id(entity.getproduct_id());
            existing.setstock_quantity(entity.getstock_quantity());
            existing.setwarehouse_location(entity.getwarehouse_location());
            existing.setreturn_stock(entity.getreturn_stock());

            warehouseRepository.save(existing);

            log.info("Warehouse " + id + " updated successfully");

            return "Warehouse " + id + " Data Updated Successfully";

        } else {

            entity.setwarehouse_id(id);
            warehouseRepository.save(entity);

            log.info("New warehouse " + id + " created");

            return "Warehouse " + id + " was not exist. So new warehouse created";
        }
    }

    // PATCH WAREHOUSE BY ID
    @Operation(
            summary = "Partially update an existing warehouse by ID",
            description = "Update specific fields of an existing warehouse record."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Warehouse updated successfully"),
        @ApiResponse(responseCode = "404", description = "Warehouse not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "500", description = "Database error occurred")
    })
    @PatchMapping("/warehouses/{id}")
    public String updateWarehouseByIdPatch(
            @PathVariable("id") Integer id,
            @RequestBody WarehouseEntity entity) {

        log.debug("Partially updating warehouse by ID: " + id);

        Optional<WarehouseEntity> tempWarehouse = warehouseRepository.findById(id);

        if (tempWarehouse.isPresent()) {

            WarehouseEntity existingWarehouse = tempWarehouse.get();

            if (entity.getproduct_id() != null) {
                existingWarehouse.setproduct_id(entity.getproduct_id());
            }

            if (entity.getstock_quantity() != null) {
                existingWarehouse.setstock_quantity(entity.getstock_quantity());
            }

            if (entity.getwarehouse_location() != null) {
                existingWarehouse.setwarehouse_location(entity.getwarehouse_location());
            }

            if (entity.getreturn_stock() != null) {
                existingWarehouse.setreturn_stock(entity.getreturn_stock());
            }

            warehouseRepository.save(existingWarehouse);

            log.info("Warehouse " + id + " partially updated");

            return "Warehouse " + id + " Data Updated Successfully";

        } else {

            return "Warehouse " + id + " does not exist";
        }
    }

    // DELETE WAREHOUSE
    @Operation(
            summary = "Delete a warehouse by ID",
            description = "Remove a warehouse record from the database by its ID."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Warehouse deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Warehouse not found")
    })
    @DeleteMapping("/warehouses/{id}")
    public String removeWarehouseById(
            @PathVariable("id") Integer id) {

        log.debug("Deleting warehouse by ID: " + id);

        Optional<WarehouseEntity> warehouse = warehouseRepository.findById(id);

        if (warehouse.isPresent()) {

            warehouseRepository.deleteById(id);

            log.info("Warehouse " + id + " deleted");

            return "Warehouse with ID " + id + " has been deleted.";

        } else {

            return "Warehouse with ID " + id + " was not found.";
        }
    }

    // TEST ENDPOINT
    @Operation(
            summary = "Test endpoint",
            description = "A simple test endpoint to verify the Warehouse API is working."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Test successful")
    })
    @GetMapping("/warehouses/test")
    public String test() {

        return "Warehouse API is working successfully!";
    }
}