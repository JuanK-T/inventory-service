package com.linktic.challenge.inventory.domain.exception.mapper;

import com.linktic.challenge.inventory.domain.exception.InventoryDomainException;

public class InventoryMapperException extends InventoryDomainException {
    public InventoryMapperException(String message) {
        super(message);
    }

    public InventoryMapperException(String message, Throwable cause) {
        super(message, cause);
    }
}
