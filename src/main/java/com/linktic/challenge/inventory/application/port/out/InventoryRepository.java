package com.linktic.challenge.inventory.application.port.out;

import com.linktic.challenge.inventory.domain.model.Inventory;
import com.linktic.challenge.inventory.domain.model.InventoryId;

import java.util.Optional;

public interface InventoryRepository {
    Optional<Inventory> findByProductId(InventoryId productId);
    Inventory save(Inventory inventory);
}