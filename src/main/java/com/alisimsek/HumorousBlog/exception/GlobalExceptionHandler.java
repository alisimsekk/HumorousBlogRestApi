package com.alisimsek.HumorousBlog.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handlerAuthenticationException(AuthenticationException ex,HttpServletRequest request){
        return ResponseEntity.status(401).body(generateApiError(401,ex,request));
    }

    public ApiError generateApiError (int status, Exception ex, HttpServletRequest request){
        ApiError apiError = new ApiError();
        apiError.setStatusCode(status);
        apiError.setMessage(ex.getMessage());
        apiError.setPath(request.getRequestURI());
        return apiError;
    }



}
