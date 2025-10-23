package com.linktic.challenge.inventory.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para respuesta de inventario")
public record InventoryResponseDto(
        @Schema(description = "ID del producto", example = "prod001")
        String productId,

        @Schema(description = "Nombre del producto", example = "Smartphone Galaxy XZ")
        String productName,

        @Schema(description = "Cantidad disponible", example = "50")
        Integer availableQuantity,

        @Schema(description = "Cantidad total en inventario", example = "100")
        Integer totalQuantity,

        @Schema(description = "Cantidad reservada", example = "50")
        Integer reservedQuantity,

        @Schema(description = "Ubicación del inventario", example = "BODEGA_001")
        String location
) {}
