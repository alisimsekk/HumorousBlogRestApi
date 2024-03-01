package com.alisimsek.HumorousBlog.exception;

import com.alisimsek.HumorousBlog.shared.Messages;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handlerAuthenticationException(AuthenticationException ex,HttpServletRequest request){
        return ResponseEntity.status(401).body(generateApiError(401,ex,request));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiError> handleMethodArgNotValidEx(MethodArgumentNotValidException exception, WebRequest request){
        ApiError apiError = new ApiError();
        apiError.setPath(request.getDescription(false));
        String message = Messages.getMessageForLocale("humorous.error.validation", LocaleContextHolder.getLocale());
        apiError.setMessage(message);
        apiError.setStatusCode(400);
        var validationErrors = exception.getBindingResult().getFieldErrors().stream().
                collect(Collectors.toMap(FieldError::getField,FieldError::getDefaultMessage, (existing, replacing) -> existing));
        apiError.setValidationErrors(validationErrors);
        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(NotUniqueMailException.class)
    ResponseEntity<ApiError> handleUniqueMailEx(NotUniqueMailException exception,HttpServletRequest request){
        ApiError apiError = generateApiError(400,exception,request);
        apiError.setValidationErrors(exception.getValidationErrors());
        return ResponseEntity.status(400).body(apiError);
    }

    @ExceptionHandler(ActivationNotificationException.class)
    ResponseEntity<ApiError> handleActivationNotificationException(ActivationNotificationException exception,HttpServletRequest request){
        return ResponseEntity.status(502).body(generateApiError(502,exception,request));
    }

    @ExceptionHandler(InvalidTokenException.class)
    ResponseEntity<ApiError> handleInvalidTokenException(InvalidTokenException exception,HttpServletRequest request){
        return ResponseEntity.status(400).body(generateApiError(400,exception,request));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    ResponseEntity<ApiError> handleEntityNotFoundException(EntityNotFoundException exception, HttpServletRequest request){
        return ResponseEntity.status(404).body(generateApiError(404,exception,request));
    }

    public ApiError generateApiError (int status, Exception ex, HttpServletRequest request){
        ApiError apiError = new ApiError();
        apiError.setStatusCode(status);
        apiError.setMessage(ex.getMessage());
        apiError.setPath(request.getRequestURI());
        return apiError;
    }
}
