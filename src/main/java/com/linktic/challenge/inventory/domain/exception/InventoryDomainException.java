package com.linktic.challenge.inventory.domain.exception;

public abstract class InventoryDomainException extends RuntimeException {
    protected InventoryDomainException(String message) {
        super(message);
    }

    protected InventoryDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}