package com.linktic.challenge.inventory.application.port.in;

import com.linktic.challenge.inventory.application.dto.InventoryResponseDto;

public interface InventoryCommandUseCase {
    InventoryResponseDto updateStock(String productId, Integer quantityChange);
    InventoryResponseDto reduceStock(String productId, Integer quantity);
    InventoryResponseDto increaseStock(String productId, Integer quantity);
}