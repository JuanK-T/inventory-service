package com.linktic.challenge.shared.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Respuesta estandarizada para todas las operaciones de la API.
 *
 * <p>Proporciona una estructura consistente para todas las respuestas, incluyendo
 * éxito/error, datos, metadatos y información de trazabilidad.</p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Respuesta estandarizada de la API")
public class StandardResponse<T> {

    @Schema(
            description = "Indica si la operación fue exitosa",
            example = "true",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private boolean success;

    @Schema(
            description = "Código de estado HTTP",
            example = "200",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String code;

    @Schema(
            description = "Mensaje descriptivo del resultado de la operación",
            example = "Operación exitosa",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String message;

    @Schema(
            description = "Datos de la respuesta. Tipo variable según la operación",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            nullable = true
    )
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private T data;

    @Builder.Default
    @Schema(
            description = "Lista de errores detallados. Vacía si no hay errores",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private List<ErrorDetail> errors = List.of();

    @Builder.Default
    @Schema(
            description = "Metadatos adicionales de la respuesta",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private Map<String, Object> meta = Map.of();

    @Builder.Default
    @Schema(
            description = "Enlaces HATEOAS para navegación",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            example = "{\"self\": \"/api/v1/users/1\", \"related\": \"/api/v1/users/1/orders\"}"
    )
    private Map<String, String> links = Map.of();

    @Schema(
            description = "ID de correlación para trazabilidad de la solicitud",
            example = "123e4567-e89b-12d3-a456-426614174000",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String correlationId;

    @Schema(
            description = "Marca de tiempo de la respuesta en formato ISO-8601",
            example = "2023-10-05T14:30:00Z",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String timestamp;
}