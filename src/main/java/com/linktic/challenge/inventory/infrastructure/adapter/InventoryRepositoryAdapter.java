package com.linktic.challenge.inventory.infrastructure.adapter;

import com.linktic.challenge.inventory.application.port.out.InventoryRepository;
import com.linktic.challenge.inventory.domain.model.Inventory;
import com.linktic.challenge.inventory.domain.model.InventoryId;
import com.linktic.challenge.inventory.domain.model.StockQuantity;
import com.linktic.challenge.inventory.infrastructure.persistence.entity.InventoryEntity;
import com.linktic.challenge.inventory.infrastructure.persistence.repository.InventoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class InventoryRepositoryAdapter implements InventoryRepository {

    private final InventoryJpaRepository inventoryJpaRepository; // Cambiar esto

    @Override
    public Optional<Inventory> findByProductId(InventoryId productId) {
        return inventoryJpaRepository.findByProductId(productId.value())
                .map(this::toDomain);
    }

    @Override
    public Inventory save(Inventory inventory) {
        InventoryEntity entity = toEntity(inventory);
        InventoryEntity savedEntity = inventoryJpaRepository.save(entity);
        return toDomain(savedEntity);
    }

    private Inventory toDomain(InventoryEntity entity) {
        return Inventory.create(
                InventoryId.of(entity.getProductId()),
                StockQuantity.of(entity.getQuantity())
        );
    }

    private InventoryEntity toEntity(Inventory inventory) {
        return new InventoryEntity(
                inventory.productId().value(),
                inventory.quantity().value(),
                null, // createdAt se genera automáticamente
                null  // updatedAt se genera automáticamente
        );
    }
}
