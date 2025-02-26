package org.example.expert.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
public class LogAspect {

    @Pointcut("execution(* org.example.expert.domain.user.controller.UserAdminController.changeUserRole(..))")
    public void userAdminController() {
    }

    @Pointcut("execution(* org.example.expert.domain.comment.controller.CommentAdminController.CommentAdminController(..))")
    public void commentAdminController() {
    }

    @Around("userAdminController() || commentAdminController()")
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {
        // 메서드 실행 전
        Class clazz = joinPoint.getTarget().getClass();
        Logger logger = LoggerFactory.getLogger(clazz);
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
        Long userId = (Long) request.getAttribute("userId");
        LocalDateTime requestTime = LocalDateTime.now();
        String requestBody = getRequestBody(request);
        Map<String, Object> params = new HashMap<>();

        try{
            String decodedURL = URLDecoder.decode(request.getRequestURI(), "UTF-8");
            params.put("userId", userId);
            params.put("requestTime", requestTime);
            params.put("requestUrl", decodedURL);
            params.put("requestBody", requestBody);
        }catch (Exception e) {
            logger.error("LoggerAspect error", e);
        }

        log.info("Request - User : {} | Time : {} | Url : {} | Body : {}",
                params.get("userId"),
                params.get("requestTime"),
                params.get("requestUrl"),
                params.get("requestBody"));

        // 메서드 실행
        Object result = joinPoint.proceed();

        // 메서드 실행 후
        LocalDateTime responseTime = LocalDateTime.now();
        String responseBody = getResponseBody(result);

        params.put("responseTime", responseTime);
        params.put("responseBody", responseBody);

        log.info("Response - User : {} | Time : {} | Url : {} | Body : {}",
                params.get("userId"),
                params.get("responseTime"),
                params.get("requestUrl"),
                params.get("responseBody"));

        return result;
    }

    private static String getResponseBody(Object result) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode resultNode = objectMapper.valueToTree(result);
        JsonNode bodyNode = resultNode.path("body");
        return bodyNode.toString();
    }

    public String getRequestBody(HttpServletRequest request) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(request.getInputStream()).toString();
    }

}
