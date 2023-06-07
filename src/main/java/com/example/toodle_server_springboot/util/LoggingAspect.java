package com.example.toodle_server_springboot.util;

import com.example.toodle_server_springboot.dto.request.project.ProjectRequest;
import com.example.toodle_server_springboot.dto.security.UserPrincipal;
import com.example.toodle_server_springboot.service.UserRequestLogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Optional;

@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {

    private final UserRequestLogService userRequestLogService;
    private final ObjectMapper objectMapper;

    @Pointcut("@annotation(LogCreateRequest)")
    public void logRequestPointcut() {
    }

    @AfterReturning(pointcut = "logRequestPointcut() && execution(* com.example.toodle_server_springboot.controller.*.*(..))", returning = "response")
    public void logRequest(JoinPoint joinPoint, Object response) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        // 어노테이션 값 추출
        LogCreateRequest annotation = method.getAnnotation(LogCreateRequest.class);
        String requestURL = annotation.requestURL();
        HttpMethod requestMethod = annotation.requestMethod();

        // 인자 값 추출
        Object[] args = joinPoint.getArgs();
        UserPrincipal userPrincipal = getUserPrincipalArgument(args);
        ProjectRequest projectRequest = getProjectRequestArgument(args);

        // 로깅 작업 수행
        assert userPrincipal != null;
        String userId = userPrincipal.email();
        HttpStatus responseCode = HttpStatus.OK;
        Optional<ProjectRequest> userRequestData = Optional.ofNullable(projectRequest);
        String userResponseData = response.toString();

        // UserRequestLogService 호출
        if (userRequestData.isPresent()) {
            userRequestLogService.createLog(userId, requestURL, requestMethod, responseCode, userRequestData.get().toString().substring(0, 4000), userResponseData.substring(0, 4000));
        }
        else {
            userRequestLogService.createLog(userId, requestURL, requestMethod, responseCode, null, userResponseData.substring(0, 4000));
        }
    }

    private UserPrincipal getUserPrincipalArgument(Object[] args) {
        for (Object arg : args) {
            if (arg instanceof UserPrincipal) {
                return (UserPrincipal) arg;
            }
        }
        return null;
    }

    private ProjectRequest getProjectRequestArgument(Object[] args) {
        for (Object arg : args) {
            if (arg instanceof ProjectRequest) {
                return (ProjectRequest) arg;
            }
        }
        return null;
    }


}
