package com.linktic.challenge.inventory.domain.model;

import com.linktic.challenge.inventory.domain.exception.valueobject.InvalidStockQuantityException;

public record StockQuantity(Integer value) {

    public static final int MAX_QUANTITY = 1_000_000;

    public StockQuantity {
        if (value == null) {
            throw new InvalidStockQuantityException("Stock quantity cannot be null");
        }

        if (value < 0) {
            throw new InvalidStockQuantityException("Stock quantity cannot be negative: " + value);
        }

        if (value > MAX_QUANTITY) {
            throw new InvalidStockQuantityException(
                    String.format("Stock quantity cannot exceed %d: %d", MAX_QUANTITY, value)
            );
        }
    }

    public StockQuantity subtract(StockQuantity other) {
        int result = this.value - other.value;
        if (result < 0) {
            throw new InvalidStockQuantityException(
                    String.format("Insufficient stock: %d - %d = %d", this.value, other.value, result)
            );
        }
        return new StockQuantity(result);
    }

    public StockQuantity add(StockQuantity other) {
        try {
            int result = Math.addExact(this.value, other.value);
            return new StockQuantity(result);
        } catch (ArithmeticException e) {
            throw new InvalidStockQuantityException("Stock quantity addition would overflow");
        }
    }

    public boolean isZero() {
        return this.value == 0;
    }

    public boolean isGreaterThan(StockQuantity other) {
        return this.value > other.value;
    }

    public static StockQuantity of(Integer value) {
        return new StockQuantity(value);
    }

    public static StockQuantity zero() {
        return new StockQuantity(0);
    }
}