package com.linktic.challenge.shared.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * Detalle de error para respuestas de API estandarizadas.
 *
 * <p>Proporciona información estructurada sobre errores específicos que ocurren durante el procesamiento de una solicitud.</p>
 */
@Data
@Builder
@Schema(description = "Detalle específico de un error en la respuesta de la API")
public class ErrorDetail {

    @Schema(
            description = "Código de error específico del dominio",
            example = "VALIDATION_ERROR",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String code;

    @Schema(
            description = "Mensaje descriptivo del error",
            example = "El campo email es requerido",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String message;

    @Schema(
            description = "Información adicional o contexto sobre el error",
            example = "El campo debe ser una dirección de correo válida",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String details;
}
