package com.linktic.challenge.inventory.domain.model;

public record Inventory(
        InventoryId productId,
        StockQuantity quantity
) {

    public Inventory updateQuantity(StockQuantity newQuantity) {
        if (this.quantity.equals(newQuantity)) {
            return this;
        }

        return new Inventory(this.productId, newQuantity);
    }

    public Inventory reduceStock(StockQuantity quantityToReduce) {
        if (quantityToReduce.isZero()) {
            return this;
        }

        StockQuantity newQuantity = this.quantity.subtract(quantityToReduce);
        return new Inventory(this.productId, newQuantity);
    }

    public Inventory increaseStock(StockQuantity quantityToAdd) {

        if (quantityToAdd.isZero()) {
            return this;
        }

        StockQuantity newQuantity = this.quantity.add(quantityToAdd);
        return new Inventory(this.productId, newQuantity);
    }

    public boolean hasSufficientStock(StockQuantity requested) {
        return this.quantity.value() >= requested.value();
    }

    public boolean isOutOfStock() {
        return this.quantity.isZero();
    }

    public InventoryUpdatedEvent createUpdateEvent(StockQuantity oldQuantity) {
        return new InventoryUpdatedEvent(this.productId, oldQuantity, this.quantity);
    }

    // Factory method
    public static Inventory create(InventoryId productId, StockQuantity initialQuantity) {
        return new Inventory(productId, initialQuantity);
    }
}