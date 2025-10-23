package com.linktic.challenge.inventory.domain.exception.entity;

import com.linktic.challenge.inventory.domain.exception.InventoryDomainException;

public abstract class InventoryEntityException extends InventoryDomainException {
    protected InventoryEntityException(String message) {
        super(message);
    }

    protected InventoryEntityException(String message, Throwable cause) {
        super(message, cause);
    }
}