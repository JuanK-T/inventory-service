package com.linktic.challenge.inventory.application.service;

import com.linktic.challenge.inventory.application.dto.InventoryResponseDto;
import com.linktic.challenge.inventory.application.port.in.InventoryCommandUseCase;
import com.linktic.challenge.inventory.application.port.in.InventoryQueryUseCase;
import com.linktic.challenge.inventory.application.port.out.InventoryEventPublisher;
import com.linktic.challenge.inventory.application.port.out.InventoryRepository;
import com.linktic.challenge.inventory.domain.model.Inventory;
import com.linktic.challenge.inventory.domain.model.InventoryId;
import com.linktic.challenge.inventory.domain.model.InventoryUpdatedEvent;
import com.linktic.challenge.inventory.domain.model.StockQuantity;
import com.linktic.challenge.inventory.infrastructure.clients.ProductServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class InventoryCommandService implements InventoryCommandUseCase {

    private final InventoryRepository inventoryRepository;
    private final ProductServiceClient productServiceClient;
    private final InventoryEventPublisher eventPublisher;
    private final InventoryQueryUseCase inventoryQueryUseCase; // Inyectar el use case de consulta

    @Override
    public InventoryResponseDto reduceStock(String productId, Integer quantity) {
        log.info("Reduciendo stock del producto {} en {}", productId, quantity);

        InventoryId inventoryId = InventoryId.of(productId);
        StockQuantity quantityToReduce = StockQuantity.of(quantity);

        // Obtener inventario actual
        Inventory inventory = inventoryRepository.findByProductId(inventoryId)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado: " + productId));

        // Reducir stock
        Inventory updatedInventory = inventory.reduceStock(quantityToReduce);

        // Guardar cambios
        Inventory savedInventory = inventoryRepository.save(updatedInventory);

        // Publicar evento
        InventoryUpdatedEvent event = new InventoryUpdatedEvent(
                inventoryId,
                inventory.quantity(),
                savedInventory.quantity()
        );
        eventPublisher.publish(event);

        // Reutilizar la consulta existente
        return inventoryQueryUseCase.getInventoryByProductId(productId);
    }

    @Override
    public InventoryResponseDto increaseStock(String productId, Integer quantity) {
        log.info("Aumentando stock del producto {} en {}", productId, quantity);

        InventoryId inventoryId = InventoryId.of(productId);
        StockQuantity quantityToAdd = StockQuantity.of(quantity);

        Inventory inventory = inventoryRepository.findByProductId(inventoryId)
                .orElseGet(() -> Inventory.create(inventoryId, StockQuantity.zero()));

        Inventory updatedInventory = inventory.increaseStock(quantityToAdd);
        Inventory savedInventory = inventoryRepository.save(updatedInventory);

        // Publicar evento
        InventoryUpdatedEvent event = new InventoryUpdatedEvent(
                inventoryId,
                inventory.quantity(),
                savedInventory.quantity()
        );
        eventPublisher.publish(event);

        // Reutilizar la consulta existente
        return inventoryQueryUseCase.getInventoryByProductId(productId);
    }

    @Override
    public InventoryResponseDto updateStock(String productId, Integer quantityChange) {
        if (quantityChange > 0) {
            return increaseStock(productId, quantityChange);
        } else if (quantityChange < 0) {
            return reduceStock(productId, Math.abs(quantityChange));
        } else {
            // No change, just return current inventory usando el servicio de consulta
            return inventoryQueryUseCase.getInventoryByProductId(productId);
        }
    }

}