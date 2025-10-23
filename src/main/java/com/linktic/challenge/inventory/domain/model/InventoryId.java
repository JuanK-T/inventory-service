package com.linktic.challenge.inventory.domain.model;

import com.linktic.challenge.inventory.domain.exception.valueobject.InvalidInventoryIdException;

public record InventoryId(String value) {
    public InventoryId {
        if (value == null) {
            throw new InvalidInventoryIdException("Inventory ID cannot be null");
        }

        String trimmedValue = value.trim();

        if (trimmedValue.isEmpty()) {
            throw new InvalidInventoryIdException("Inventory ID cannot be empty");
        }

        if (trimmedValue.length() > 50) {
            throw new InvalidInventoryIdException("Inventory ID cannot exceed 50 characters");
        }

        if (!trimmedValue.matches("^[a-zA-Z0-9_-]+$")) {
            throw new InvalidInventoryIdException("Inventory ID can only contain letters, numbers, hyphens and underscores");
        }
    }

    public static InventoryId of(String value) {
        return new InventoryId(value);
    }
}