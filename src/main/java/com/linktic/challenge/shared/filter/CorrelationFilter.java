package com.linktic.challenge.shared.filter;


import com.linktic.challenge.shared.constants.CorrelationConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
public class CorrelationFilter extends OncePerRequestFilter {
    public static final String HEADER = CorrelationConstants.HEADER_NAME;

    @Override
    public void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {
        String cid = Optional.ofNullable(req.getHeader(HEADER))
                .filter(s -> !s.isBlank())
                .orElse(UUID.randomUUID().toString());

        // MDC para logs
        MDC.put(CorrelationConstants.MDC_KEY, cid);
        MDC.put(CorrelationConstants.MDC_METHOD, req.getMethod());
        MDC.put(CorrelationConstants.MDC_PATH, req.getRequestURI());

        // útil para el handler
        req.setAttribute(HEADER, cid);

        // propaga a la respuesta
        res.setHeader(HEADER, cid);

        try { chain.doFilter(req, res); }
        finally { MDC.clear(); }
    }
}
