package com.linktic.challenge.inventory.domain.exception.valueobject;

public class InvalidInventoryUpdatedEventException extends InventoryValueObjectException{
    public InvalidInventoryUpdatedEventException(String message) {
        super(message);
    }
}
