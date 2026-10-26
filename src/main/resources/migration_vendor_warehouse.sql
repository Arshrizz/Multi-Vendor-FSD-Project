-- ============================================================================
-- Migration: Warehouse-Product & Vendor-Warehouse many-to-many relationships
-- ============================================================================
-- Run this manually against your PostgreSQL database AFTER the application
-- has started once (so Hibernate's ddl-auto=update creates the new tables
-- and columns first).
--
-- WARNING: Steps marked with "DROP" will permanently remove columns.
-- Make sure the INSERT steps above them have migrated the data first.
-- ============================================================================

-- ============================================================================
-- PART 1: Vendor <-> Warehouse (many-to-many)
-- ============================================================================

-- Step 1a: Migrate existing vendor->warehouse mappings to the junction table.
-- The vendor_warehouse table is auto-created by Hibernate's ddl-auto=update.
INSERT INTO vendor_warehouse (vendor_id, warehouse_id)
SELECT vendor_id, warehouse_id
FROM vendor
WHERE warehouse_id IS NOT NULL
ON CONFLICT DO NOTHING;

-- Step 1b: Drop the old warehouse_id column from vendor.
-- Hibernate ddl-auto=update never drops columns, so this must be manual.
ALTER TABLE vendor DROP COLUMN IF EXISTS warehouse_id;

-- ============================================================================
-- PART 2: Warehouse <-> Product (many-to-many via warehouse_product)
-- ============================================================================

-- Step 2a: Migrate existing warehouse->product mappings to the junction table.
-- The warehouse_product table is auto-created by Hibernate's ddl-auto=update.
INSERT INTO warehouse_product (warehouse_id, product_id)
SELECT warehouse_id, product_id
FROM warehouse
WHERE product_id IS NOT NULL
ON CONFLICT DO NOTHING;

-- Step 2b: Drop the old product_id column from warehouse.
ALTER TABLE warehouse DROP COLUMN IF EXISTS product_id;

-- ============================================================================
-- PART 3: ON DELETE constraints
-- ============================================================================

-- Warehouse -> Address: ON DELETE SET NULL
-- If an address is deleted, the warehouse keeps existing without a location.
ALTER TABLE warehouse DROP CONSTRAINT IF EXISTS fk_warehouse_address;
ALTER TABLE warehouse ADD CONSTRAINT fk_warehouse_address
    FOREIGN KEY (warehouse_location) REFERENCES "Address"(address_id) ON DELETE SET NULL;

-- WarehouseProduct -> Warehouse: ON DELETE CASCADE
ALTER TABLE warehouse_product DROP CONSTRAINT IF EXISTS fk_wp_warehouse;
ALTER TABLE warehouse_product ADD CONSTRAINT fk_wp_warehouse
    FOREIGN KEY (warehouse_id) REFERENCES warehouse(warehouse_id) ON DELETE CASCADE;

-- WarehouseProduct -> Product: ON DELETE CASCADE
ALTER TABLE warehouse_product DROP CONSTRAINT IF EXISTS fk_wp_product;
ALTER TABLE warehouse_product ADD CONSTRAINT fk_wp_product
    FOREIGN KEY (product_id) REFERENCES product(product_id) ON DELETE CASCADE;

-- VendorWarehouse -> Vendor: ON DELETE CASCADE
ALTER TABLE vendor_warehouse DROP CONSTRAINT IF EXISTS fk_vw_vendor;
ALTER TABLE vendor_warehouse ADD CONSTRAINT fk_vw_vendor
    FOREIGN KEY (vendor_id) REFERENCES vendor(vendor_id) ON DELETE CASCADE;

-- VendorWarehouse -> Warehouse: ON DELETE CASCADE
ALTER TABLE vendor_warehouse DROP CONSTRAINT IF EXISTS fk_vw_warehouse;
ALTER TABLE vendor_warehouse ADD CONSTRAINT fk_vw_warehouse
    FOREIGN KEY (warehouse_id) REFERENCES warehouse(warehouse_id) ON DELETE CASCADE;
