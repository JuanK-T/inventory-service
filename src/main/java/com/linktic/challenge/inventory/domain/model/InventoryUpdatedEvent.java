package com.linktic.challenge.inventory.domain.model;

import com.linktic.challenge.inventory.domain.exception.valueobject.InvalidInventoryUpdatedEventException;

import java.time.LocalDateTime;

public record InventoryUpdatedEvent(
        InventoryId productId,
        StockQuantity oldQuantity,
        StockQuantity newQuantity,
        LocalDateTime timestamp
) {

    public InventoryUpdatedEvent {
        if (oldQuantity.equals(newQuantity)) {
            throw new IllegalArgumentException("Inventory update event requires actual quantity change");
        }

        timestamp = LocalDateTime.now();
    }

    public InventoryUpdatedEvent(InventoryId productId, StockQuantity oldQuantity, StockQuantity newQuantity) {
        this(productId, oldQuantity, newQuantity, LocalDateTime.now());
    }

    public boolean isStockIncrease() {
        return newQuantity.value() > oldQuantity.value();
    }

    public boolean isStockDecrease() {
        return newQuantity.value() < oldQuantity.value();
    }

    public StockQuantity getChangeAmount() {
        int change = Math.abs(newQuantity.value() - oldQuantity.value());
        return StockQuantity.of(change);
    }

    public String toMessage() {
        String changeType = isStockIncrease() ? "INCREASE" : "DECREASE";
        return String.format(
                "🔄 INVENTORY_%s - Product: %s, Old: %d, New: %d, Change: %d, Time: %s",
                changeType, productId.value(), oldQuantity.value(), newQuantity.value(),
                getChangeAmount().value(), timestamp
        );
    }

    // Factory method para crear evento desde un inventory anterior y actual
    public static InventoryUpdatedEvent from(Inventory oldInventory, Inventory newInventory) {
        // Debe verificar que SON el mismo producto, no que son diferentes
        if (!oldInventory.productId().equals(newInventory.productId())) {
            throw new InvalidInventoryUpdatedEventException("Cannot create event for different products");
        }

        return new InventoryUpdatedEvent(
                newInventory.productId(),
                oldInventory.quantity(),
                newInventory.quantity()
        );
    }
}