package com.linktic.challenge.inventory.domain.exception.valueobject;

public class InvalidStockQuantityException extends InventoryValueObjectException{
    public InvalidStockQuantityException(String message) {
        super(message);
    }
}
