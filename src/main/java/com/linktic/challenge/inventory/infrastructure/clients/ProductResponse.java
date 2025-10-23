package com.linktic.challenge.inventory.infrastructure.clients;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.Map;

@Schema(description = "Respuesta del servicio de productos")
public record ProductResponse(
        @Schema(description = "Indica si la operación fue exitosa")
        @JsonProperty("success") boolean success,

        @Schema(description = "Código de estado HTTP")
        @JsonProperty("code") String code,

        @Schema(description = "Mensaje descriptivo")
        @JsonProperty("message") String message,

        @Schema(description = "Datos del producto")
        @JsonProperty("data") ProductData data,

        @Schema(description = "ID de correlación")
        @JsonProperty("correlationId") String correlationId,

        @Schema(description = "Marca de tiempo")
        @JsonProperty("timestamp") String timestamp
) {

    @Schema(description = "Datos del producto")
    public record ProductData(
            @Schema(description = "ID del producto", example = "prod001")
            @JsonProperty("id") String id,

            @Schema(description = "Nombre del producto", example = "Smartphone Galaxy XZ")
            @JsonProperty("name") String name,

            @Schema(description = "URL de la imagen", example = "https://example.com/images/smartphone-xz.jpg")
            @JsonProperty("imageUrl") String imageUrl,

            @Schema(description = "Descripción del producto")
            @JsonProperty("description") String description,

            @Schema(description = "Precio del producto", example = "899.99")
            @JsonProperty("price") BigDecimal price,

            @Schema(description = "Moneda", example = "USD")
            @JsonProperty("currency") String currency,

            @Schema(description = "Rating del producto", example = "4.7")
            @JsonProperty("rating") Double rating,

            @Schema(description = "Categoría", example = "Electrónica")
            @JsonProperty("category") String category,

            @Schema(description = "Marca", example = "TechNova")
            @JsonProperty("brand") String brand,

            @Schema(description = "Especificaciones técnicas")
            @JsonProperty("specifications") Map<String, String> specifications
    ) {}
}