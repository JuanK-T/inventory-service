package com.linktic.challenge.inventory.domain.exception.valueobject;

import com.linktic.challenge.inventory.domain.exception.InventoryDomainException;

public abstract class InventoryValueObjectException extends InventoryDomainException {
    protected InventoryValueObjectException(String message) {
        super(message);
    }
}