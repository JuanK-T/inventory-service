package com.linktic.challenge.shared.constants;

public final class CorrelationConstants {
    public static final String HEADER_NAME = "X-Correlation-Id";
    public static final String MDC_KEY = "correlationId";
    public static final String MDC_PATH = "path";
    public static final String MDC_METHOD = "method";

    private CorrelationConstants() {}
}