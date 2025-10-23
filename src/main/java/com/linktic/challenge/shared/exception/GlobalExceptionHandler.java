package com.linktic.challenge.shared.exception;

import com.linktic.challenge.shared.response.ErrorDetail;
import com.linktic.challenge.shared.response.StandardResponse;
import com.linktic.challenge.shared.util.StandardResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // ========== EXCEPCIONES DE SPRING WEB ==========

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<StandardResponse<Object>> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        log.warn("Método HTTP no soportado: {} para esta URL", ex.getMethod());

        ErrorDetail errorDetail = StandardResponses.errorDetail(
                "METHOD_NOT_ALLOWED",
                "Método no permitido",
                String.format("El método %s no está soportado para esta operación", ex.getMethod())
        );

        StandardResponse<Object> response = StandardResponses.errorResponse(
                String.valueOf(HttpStatus.METHOD_NOT_ALLOWED.value()),
                "Operación no permitida",
                List.of(errorDetail)
        );

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<StandardResponse<Object>> handleMessageNotReadable(HttpMessageNotReadableException ex) {
        log.warn("Cuerpo de request no legible: {}", ex.getMessage());

        ErrorDetail errorDetail = StandardResponses.errorDetail(
                "INVALID_JSON",
                "JSON inválido",
                "El cuerpo de la solicitud contiene JSON mal formado o incompleto"
        );

        StandardResponse<Object> response = StandardResponses.errorResponse(
                String.valueOf(HttpStatus.BAD_REQUEST.value()),
                "Formato de datos inválido",
                List.of(errorDetail)
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<StandardResponse<Object>> handleMissingParameter(MissingServletRequestParameterException ex) {
        log.warn("Parámetro requerido faltante: {}", ex.getParameterName());

        ErrorDetail errorDetail = StandardResponses.errorDetail(
                "MISSING_PARAMETER",
                "Parámetro requerido faltante",
                String.format("El parámetro '%s' es requerido", ex.getParameterName())
        );

        StandardResponse<Object> response = StandardResponses.errorResponse(
                String.valueOf(HttpStatus.BAD_REQUEST.value()),
                "Faltan parámetros requeridos",
                List.of(errorDetail)
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<StandardResponse<Object>> handleNoHandlerFound(NoHandlerFoundException ex) {
        log.warn("Endpoint no encontrado: {} {}", ex.getHttpMethod(), ex.getRequestURL());

        ErrorDetail errorDetail = StandardResponses.errorDetail(
                "ENDPOINT_NOT_FOUND",
                "Endpoint no encontrado",
                String.format("La ruta %s no existe", ex.getRequestURL())
        );

        StandardResponse<Object> response = StandardResponses.errorResponse(
                String.valueOf(HttpStatus.NOT_FOUND.value()),
                "Recurso no encontrado",
                List.of(errorDetail)
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // ========== EXCEPCIONES DE JAVA COMUNES ==========

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<StandardResponse<Object>> handleIllegalArgument(IllegalArgumentException ex) {
        log.warn("Argumento ilegal: {}", ex.getMessage());

        ErrorDetail errorDetail = StandardResponses.errorDetail(
                "ILLEGAL_ARGUMENT",
                "Argumento inválido",
                ex.getMessage()
        );

        StandardResponse<Object> response = StandardResponses.errorResponse(
                String.valueOf(HttpStatus.BAD_REQUEST.value()),
                "Parámetros de solicitud inválidos",
                List.of(errorDetail)
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<StandardResponse<Object>> handleNullPointer(NullPointerException ex) {
        log.error("Null pointer exception: {}", ex.getMessage(), ex);

        ErrorDetail errorDetail = StandardResponses.errorDetail(
                "INTERNAL_ERROR",
                "Error interno del servidor",
                "Ocurrió un error inesperado procesando la solicitud"
        );

        StandardResponse<Object> response = StandardResponses.errorResponse(
                String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                "Error interno del sistema",
                List.of(errorDetail)
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<StandardResponse<Object>> handleIllegalState(IllegalStateException ex) {
        log.warn("Estado ilegal: {}", ex.getMessage());

        ErrorDetail errorDetail = StandardResponses.errorDetail(
                "ILLEGAL_STATE",
                "Estado inválido del sistema",
                ex.getMessage()
        );

        StandardResponse<Object> response = StandardResponses.errorResponse(
                String.valueOf(HttpStatus.CONFLICT.value()),
                "Operación no permitida en el estado actual",
                List.of(errorDetail)
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    // ========== EXCEPCIÓN GENÉRICA ==========

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardResponse<Object>> handleGenericException(Exception ex) {
        log.error("Excepción no manejada: {}", ex.getMessage(), ex);

        ErrorDetail errorDetail = StandardResponses.errorDetail(
                "UNEXPECTED_ERROR",
                "Error inesperado",
                "Ocurrió un error inesperado en el servidor"
        );

        StandardResponse<Object> response = StandardResponses.errorResponse(
                String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                "Error interno del servidor",
                List.of(errorDetail)
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    // ========== EXCEPCIONES DE BASE DE DATOS ==========

    @ExceptionHandler(org.springframework.dao.DataAccessException.class)
    public ResponseEntity<StandardResponse<Object>> handleDataAccessException(org.springframework.dao.DataAccessException ex) {
        log.error("Error de acceso a datos: {}", ex.getMessage(), ex);

        ErrorDetail errorDetail = StandardResponses.errorDetail(
                "DATABASE_ERROR",
                "Error de base de datos",
                "Ocurrió un error al acceder a los datos"
        );

        StandardResponse<Object> response = StandardResponses.errorResponse(
                String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                "Error en el almacenamiento de datos",
                List.of(errorDetail)
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}