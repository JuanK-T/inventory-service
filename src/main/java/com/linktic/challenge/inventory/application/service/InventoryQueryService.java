package com.linktic.challenge.inventory.application.service;

import com.linktic.challenge.inventory.application.dto.InventoryResponseDto;
import com.linktic.challenge.inventory.application.port.in.InventoryQueryUseCase;
import com.linktic.challenge.inventory.application.port.out.InventoryRepository;
import com.linktic.challenge.inventory.domain.model.Inventory;
import com.linktic.challenge.inventory.domain.model.InventoryId;
import com.linktic.challenge.inventory.domain.model.StockQuantity;
import com.linktic.challenge.inventory.infrastructure.clients.ProductResponse;
import com.linktic.challenge.inventory.infrastructure.clients.ProductServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InventoryQueryService implements InventoryQueryUseCase {

    private final InventoryRepository inventoryRepository;
    private final ProductServiceClient productServiceClient;

    @Override
    public InventoryResponseDto getInventoryByProductId(String productId) {
        log.info("Consultando inventario para el producto: {}", productId);

        // 1. Validar y crear Value Object del ID
        InventoryId inventoryId = InventoryId.of(productId);

        // 2. Obtener información del producto del microservicio de productos
        ProductResponse productResponse = productServiceClient.getProductById(productId);

        if (!productResponse.success()) {
            throw new RuntimeException("Producto no encontrado: " + productId);
        }

        // 3. Obtener información del inventario usando el Value Object
        Inventory inventory = inventoryRepository.findByProductId(inventoryId)
                .orElseGet(() -> createDefaultInventory(inventoryId));

        // 4. Construir respuesta combinada
        return buildInventoryResponse(productResponse, inventory);
    }

    private Inventory createDefaultInventory(InventoryId productId) {
        log.warn("No se encontró inventario para el producto: {}, creando inventario por defecto", productId.value());
        return Inventory.create(productId, StockQuantity.zero());
    }

    private InventoryResponseDto buildInventoryResponse(ProductResponse productResponse, Inventory inventory) {
        return new InventoryResponseDto(
                inventory.productId().value(),
                productResponse.data().name(),
                inventory.quantity().value(), // available quantity
                inventory.quantity().value(), // total quantity (mismo que available en tu modelo)
                0, // reserved quantity (no manejado en tu modelo actual)
                "DEFAULT_LOCATION" // location (podrías agregarlo a tu modelo si es necesario)
        );
    }
}