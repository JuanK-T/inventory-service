package com.linktic.challenge.inventory.application.port.out;

import com.linktic.challenge.inventory.domain.model.InventoryUpdatedEvent;

public interface InventoryEventPublisher {
    void publish(InventoryUpdatedEvent event);
}