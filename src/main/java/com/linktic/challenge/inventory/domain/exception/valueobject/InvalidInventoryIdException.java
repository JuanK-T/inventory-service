package com.linktic.challenge.inventory.domain.exception.valueobject;

import com.linktic.challenge.inventory.domain.exception.InventoryDomainException;

public class InvalidInventoryIdException extends InventoryDomainException {
    public InvalidInventoryIdException(String message) {
        super(message);
    }
}
