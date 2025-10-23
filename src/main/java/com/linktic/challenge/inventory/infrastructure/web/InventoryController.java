package com.linktic.challenge.inventory.infrastructure.web;

import com.linktic.challenge.inventory.application.dto.InventoryResponseDto;
import com.linktic.challenge.inventory.application.port.in.InventoryCommandUseCase;
import com.linktic.challenge.inventory.application.port.in.InventoryQueryUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
@Validated
@Tag(name = "Inventario", description = "API para gestión de inventarios - Catálogo completo")
public class InventoryController {

    private final InventoryQueryUseCase inventoryQueryUseCase;
    private final InventoryCommandUseCase inventoryCommandUseCase;

    @GetMapping("/{productId}")
    @Operation(summary = "Consultar inventario", description = "Obtiene el inventario actual de un producto específico por su ID")
    public ResponseEntity<InventoryResponseDto> getInventoryByProductId(
            @PathVariable String productId) {
        InventoryResponseDto response = inventoryQueryUseCase.getInventoryByProductId(productId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{productId}/reduce")
    @Operation(summary = "Reducir stock", description = "Reduce la cantidad de stock disponible para un producto específico")
    public ResponseEntity<InventoryResponseDto> reduceStock(
            @PathVariable String productId,
            @RequestParam Integer quantity) {
        InventoryResponseDto response = inventoryCommandUseCase.reduceStock(productId, quantity);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{productId}/increase")
    @Operation(summary = "Aumentar stock", description = "Incrementa la cantidad de stock disponible para un producto específico")
    public ResponseEntity<InventoryResponseDto> increaseStock(
            @PathVariable String productId,
            @RequestParam Integer quantity) {
        InventoryResponseDto response = inventoryCommandUseCase.increaseStock(productId, quantity);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{productId}")
    @Operation(summary = "Actualizar stock", description = "Actualiza el stock de un producto con un cambio específico (positivo o negativo)")
    public ResponseEntity<InventoryResponseDto> updateStock(
            @PathVariable String productId,
            @RequestParam Integer quantityChange) {
        InventoryResponseDto response = inventoryCommandUseCase.updateStock(productId, quantityChange);
        return ResponseEntity.ok(response);
    }
}