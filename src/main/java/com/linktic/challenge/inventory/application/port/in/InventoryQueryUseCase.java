package com.linktic.challenge.inventory.application.port.in;

import com.linktic.challenge.inventory.application.dto.InventoryResponseDto;

public interface InventoryQueryUseCase {
    InventoryResponseDto getInventoryByProductId(String productId);

}
