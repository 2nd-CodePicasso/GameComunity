package com.example.codePicasso.global.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
public class ServiceLoggingAspect {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    @Pointcut("execution(* com.example.codePicasso.domain..service.*.*(..))")
    public void serviceMethods() {}

    private static final Set<String> SENSITIVE_FIELDS = new HashSet<>(Arrays.asList("password", "contact"));

    @Around("serviceMethods()")
    public Object logServiceExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();

        String filteredRequest = filterSensitiveData(args);
        log.info("🔥 Entering Service: {} | Request: {}", methodName, filteredRequest);

        try {
            return joinPoint.proceed();
        } catch (Exception ex) {
            log.error("❌ Exception in Service: {} - {}", methodName, ex.getMessage(), ex);
            throw ex;
        }
    }

    private String filterSensitiveData(Object data) {
        try {
            if (data == null) return "null";

            if (data instanceof Page<?>) {
                return convertPageToJson((Page<?>) data);
            }

            if (data.getClass().isArray()) {
                Object[] arrayData = (Object[]) data;
                return Arrays.stream(arrayData)
                        .map(this::filterSensitiveData)
                        .collect(Collectors.toList())
                        .toString();
            }

            String jsonString = objectMapper.writeValueAsString(data);
            for (String field : SENSITIVE_FIELDS) {
                jsonString = jsonString.replaceAll("\"" + field + "\":\"[^\"]+\"", "\"" + field + "\":\"****\"");
            }
            return jsonString;
        } catch (Exception e) {
            log.warn("⚠️ Failed to parse JSON for logging: {}", e.getMessage());
            return data.toString();
        }
    }

    private <T> String convertPageToJson(Page<T> page) {
        try {
            PageResponse<T> pageResponse = new PageResponse<>(
                    page.getContent(),
                    page.getNumber(),
                    page.getTotalPages(),
                    page.getTotalElements()
            );
            return objectMapper.writeValueAsString(pageResponse);
        } catch (Exception e) {
            log.warn("⚠️ Failed to convert Page to JSON: {}", e.getMessage());
            return "Page Serialization Error";
        }
    }
}
