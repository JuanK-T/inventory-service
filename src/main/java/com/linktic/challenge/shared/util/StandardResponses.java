package com.linktic.challenge.shared.util;

import com.linktic.challenge.shared.constants.CorrelationConstants;
import com.linktic.challenge.shared.response.ErrorDetail;
import com.linktic.challenge.shared.response.StandardResponse;
import lombok.experimental.UtilityClass;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Utility class con métodos factory para crear respuestas de API estandarizadas.
 *
 * <p>Proporciona métodos estáticos para crear respuestas comunes con configuraciones
 * predefinidas, asegurando consistencia en toda la aplicación.</p>
 *
 * <p><b>Características principales:</b></p>
 * <ul>
 *   <li>Generación automática de correlationId desde MDC o UUID</li>
 *   <li>Marca de tiempo en UTC</li>
 *   <li>Protección contra valores nulos en colecciones</li>
 *   <li>Configuración consistente de metadatos</li>
 *   <li>Mensajes personalizables para diferentes escenarios</li>
 * </ul>
 */
@UtilityClass
public class StandardResponses {

    /**
     * Clave utilizada en MDC para almacenar el ID de correlación
     */
    public static final String MDC_CORRELATION_KEY = CorrelationConstants.MDC_KEY;

    /**
     * Clave utilizada en MDC para almacenar el Path
     */
    public static final String MDC_PATH_KEY = CorrelationConstants.MDC_PATH;

    // Mensajes predefinidos para diferentes operaciones
    public static final String DEFAULT_SUCCESS_MESSAGE = "Operación exitosa";
    public static final String CREATED_MESSAGE = "Recurso creado exitosamente";
    public static final String UPDATED_MESSAGE = "Recurso actualizado exitosamente";
    public static final String DELETED_MESSAGE = "Recurso eliminado exitosamente";
    public static final String RETRIEVED_MESSAGE = "Recurso obtenido exitosamente";

    /**
     * Crea una respuesta exitosa con mensaje por defecto.
     *
     * @param <T> tipo de los datos
     * @param data datos a incluir en la respuesta
     * @return StandardResponse con estado 200 OK
     */
    public static <T> StandardResponse<T> ok(T data) {
        return ok(data, DEFAULT_SUCCESS_MESSAGE);
    }

    /**
     * Crea una respuesta exitosa con mensaje personalizado.
     *
     * @param <T> tipo de los datos
     * @param data datos a incluir en la respuesta
     * @param message mensaje personalizado
     * @return StandardResponse con estado 200 OK
     */
    public static <T> StandardResponse<T> ok(T data, String message) {
        return base(true, String.valueOf(HttpStatus.OK.value()), message, data, List.of(), Map.of(), Map.of());
    }

    /**
     * Crea una respuesta para creación exitosa de recursos.
     *
     * @param <T> tipo de los datos
     * @param data datos del recurso creado
     * @return StandardResponse con estado 200 OK y mensaje de creación
     */
    public static <T> StandardResponse<T> created(T data) {
        return base(true, String.valueOf(HttpStatus.OK.value()), CREATED_MESSAGE, data, List.of(), Map.of(), Map.of());
    }

    /**
     * Crea una respuesta para creación exitosa con mensaje personalizado.
     *
     * @param <T> tipo de los datos
     * @param data datos del recurso creado
     * @param message mensaje personalizado
     * @return StandardResponse con estado 200 OK
     */
    public static <T> StandardResponse<T> created(T data, String message) {
        return base(true, String.valueOf(HttpStatus.OK.value()), message, data, List.of(), Map.of(), Map.of());
    }

    /**
     * Crea una respuesta para actualización exitosa.
     *
     * @param <T> tipo de los datos
     * @param data datos del recurso actualizado
     * @return StandardResponse con estado 200 OK y mensaje de actualización
     */
    public static <T> StandardResponse<T> updated(T data) {
        return base(true, String.valueOf(HttpStatus.OK.value()), UPDATED_MESSAGE, data, List.of(), Map.of(), Map.of());
    }

    /**
     * Crea una respuesta para actualización exitosa con mensaje personalizado.
     *
     * @param <T> tipo de los datos
     * @param data datos del recurso actualizado
     * @param message mensaje personalizado
     * @return StandardResponse con estado 200 OK
     */
    public static <T> StandardResponse<T> updated(T data, String message) {
        return base(true, String.valueOf(HttpStatus.OK.value()), message, data, List.of(), Map.of(), Map.of());
    }

    /**
     * Crea una respuesta para eliminación exitosa.
     *
     * @param <T> tipo de los datos
     * @param data datos adicionales (puede ser mensaje de confirmación)
     * @return StandardResponse con estado 200 OK y mensaje de eliminación
     */
    public static <T> StandardResponse<T> deleted(T data) {
        return base(true, String.valueOf(HttpStatus.OK.value()), DELETED_MESSAGE, data, List.of(), Map.of(), Map.of());
    }

    /**
     * Crea una respuesta para eliminación exitosa con mensaje personalizado.
     *
     * @param <T> tipo de los datos
     * @param data datos adicionales
     * @param message mensaje personalizado
     * @return StandardResponse con estado 200 OK
     */
    public static <T> StandardResponse<T> deleted(T data, String message) {
        return base(true, String.valueOf(HttpStatus.OK.value()), message, data, List.of(), Map.of(), Map.of());
    }

    /**
     * Crea una respuesta para obtención exitosa de recursos.
     *
     * @param <T> tipo de los datos
     * @param data datos obtenidos
     * @return StandardResponse con estado 200 OK y mensaje de obtención
     */
    public static <T> StandardResponse<T> retrieved(T data) {
        return base(true, String.valueOf(HttpStatus.OK.value()), RETRIEVED_MESSAGE, data, List.of(), Map.of(), Map.of());
    }

    /**
     * Crea una respuesta para obtención exitosa con mensaje personalizado.
     *
     * @param <T> tipo de los datos
     * @param data datos obtenidos
     * @param message mensaje personalizado
     * @return StandardResponse con estado 200 OK
     */
    public static <T> StandardResponse<T> retrieved(T data, String message) {
        return base(true, String.valueOf(HttpStatus.OK.value()), message, data, List.of(), Map.of(), Map.of());
    }

    /**
     * Crea una respuesta de error personalizada.
     *
     * @param <T> tipo de los datos
     * @param code código de error
     * @param message mensaje de error
     * @param errors lista de errores detallados
     * @return StandardResponse con información de error
     */
    public static <T> StandardResponse<T> errorResponse(String code,
                                                        String message,
                                                        List<ErrorDetail> errors) {
        Map<String, Object> meta = Map.of("path", resolvePath());
        return base(false, code, message, null, errors, meta, Map.of());
    }

    /**
     * Crea un detalle de error específico.
     *
     * @param code código del error
     * @param message mensaje del error
     * @param details detalles adicionales
     * @return ErrorDetail configurado
     */
    public static ErrorDetail errorDetail(String code, String message, String details) {
        return ErrorDetail.builder()
                .code(code)
                .message(message)
                .details(details)
                .build();
    }

    /* ======================= CORE BUILDER ======================= */

    /**
     * Método base para construir respuestas de API con toda la configuración.
     *
     * @param <T> tipo de los datos
     * @param success indica si la operación fue exitosa
     * @param code código de estado HTTP
     * @param message mensaje descriptivo
     * @param data datos de la respuesta
     * @param errors lista de errores detallados
     * @param meta metadatos adicionales
     * @param links enlaces HATEOAS
     * @return StandardResponse completamente configurada
     */
    private static <T> StandardResponse<T> base(
            boolean success,
            String code,
            String message,
            T data,
            List<ErrorDetail> errors,
            Map<String, Object> meta,
            Map<String, String> links
    ) {
        return StandardResponse.<T>builder()
                .success(success)
                .code(code)
                .message(message)
                .data(data)
                .errors(safeErrors(errors))
                .meta(safeMeta(meta))
                .links(safeLinks(links))
                .correlationId(resolveCorrelationId())
                .timestamp(OffsetDateTime.now(ZoneOffset.UTC).toString())
                .build();
    }

    /**
     * Resuelve el ID de correlación desde el MDC o genera uno nuevo.
     *
     * @return ID de correlación para tracing
     */
    private static String resolveCorrelationId() {
        String fromMdc = MDC.get(MDC_CORRELATION_KEY);
        if (fromMdc != null && !fromMdc.isBlank()) return fromMdc;
        String generated = UUID.randomUUID().toString();
        MDC.put(MDC_CORRELATION_KEY, generated);
        return generated;
    }

    private static String resolvePath() {
        String pathFromMdc = MDC.get(MDC_PATH_KEY);
        return pathFromMdc != null ? pathFromMdc : "unknown";
    }

    /**
     * Garantiza que la lista de errores nunca sea nula.
     */
    private static List<ErrorDetail> safeErrors(List<ErrorDetail> errors) {
        return errors == null ? List.of() : errors;
    }

    /**
     * Garantiza que el mapa de metadatos nunca sea nulo.
     */
    private static Map<String, Object> safeMeta(Map<String, Object> meta) {
        return meta == null ? Map.of() : meta;
    }

    /**
     * Garantiza que el mapa de enlaces nunca sea nulo.
     */
    private static Map<String, String> safeLinks(Map<String, String> links) {
        return links == null ? Map.of() : links;
    }
}
