package com.example.codePicasso.global.common;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class Logging implements Filter {

    private static final String MASK = "****";
    private static final String[] SENSITIVE_KEYS = {"password", "contact"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper((HttpServletRequest) request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper((HttpServletResponse) response);

        filterChain.doFilter(wrappedRequest, wrappedResponse);

        log.info("\n" +
                        "[Request] {} - {}\n" +
                        "Client : {}\n" +
                        "Headers : {}\n" +
                        "Request : {}\n" +
                        "Response : {}\n",
                ((HttpServletRequest) request).getMethod(),
                ((HttpServletRequest) request).getRequestURI(),
                getClient((HttpServletRequest) request),
                getHeaders((HttpServletRequest) request),
                getRequest(wrappedRequest),
                getResponse(wrappedResponse)
        );

        wrappedResponse.copyBodyToResponse();
    }

    private Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> headerMap = new HashMap<>();

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = request.getHeader(key);
            headerMap.put(key, value);
        }
        return headerMap;
    }

    private String getClient(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        return "{IP: " + ip + ", Browser: " + (userAgent != null ? userAgent : "Unknown") + "}";
    }

    private String getRequest(ContentCachingRequestWrapper wrappedRequest) {
        byte[] content = wrappedRequest.getContentAsByteArray();
        String requestBody = (content.length > 0) ? new String(content, StandardCharsets.UTF_8) : "Empty Body";
        return maskSensitiveData(requestBody);
    }

    private String getResponse(ContentCachingResponseWrapper wrappedResponse) {
        byte[] content = wrappedResponse.getContentAsByteArray();
        String responseBody = (content.length > 0) ? new String(content, StandardCharsets.UTF_8) : "Empty Body";
        return maskSensitiveData(responseBody);
    }

    private String maskSensitiveData(String data) {
        if (data == null || data.isEmpty()) {
            return data;
        }

        for (String key : SENSITIVE_KEYS) {
            data = data.replaceAll("(?i)\"" + key + "\"\\s*:\\s*\"[^\"]*\"", "\"" + key + "\": \"" + MASK + "\"");
            data = data.replaceAll("(?i)" + key + "\\s*=\\s*[^&\\s]+", key + "=" + MASK);
        }
        return data;
    }
}
