package com.alisimsek.HumorousBlog.controller;

import com.alisimsek.HumorousBlog.dto.UserCreateDto;
import com.alisimsek.HumorousBlog.entity.User;
import com.alisimsek.HumorousBlog.exception.ActivationNotificationException;
import com.alisimsek.HumorousBlog.exception.ApiError;
import com.alisimsek.HumorousBlog.exception.InvalidTokenException;
import com.alisimsek.HumorousBlog.exception.NotUniqueMailException;
import com.alisimsek.HumorousBlog.service.UserService;
import com.alisimsek.HumorousBlog.shared.GenericMessage;
import com.alisimsek.HumorousBlog.shared.Messages;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    //private final MessageSource messageSource;

    @PostMapping
    public ResponseEntity<?> createUser (@Valid @RequestBody UserCreateDto userCreateDto){
        System.err.println("----" + LocaleContextHolder.getLocale().getLanguage());
        User createdUser = userService.createUser(userCreateDto);
        if (createdUser !=null){
            String message = Messages.getMessageForLocale("humorous.create.user.success.message", LocaleContextHolder.getLocale());
            System.out.println(message);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        }
        else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/{token}/active")
    public GenericMessage activateUser(@PathVariable String token){
        userService.activateUser(token);
        String message = Messages.getMessageForLocale("humorous.activate.user.success.message",LocaleContextHolder.getLocale());
        return new GenericMessage(message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiError> handleMethodArgNotValidEx(MethodArgumentNotValidException exception){
        ApiError apiError = new ApiError();
        apiError.setPath("api/v1/user");
        String message = Messages.getMessageForLocale("humorous.error.validation", LocaleContextHolder.getLocale());
        apiError.setMessage(message);
        apiError.setStatusCode(400);
        var validationErrors = exception.getBindingResult().getFieldErrors().stream().
                collect(Collectors.toMap(FieldError::getField,FieldError::getDefaultMessage, (existing, replacing) -> existing));
        apiError.setValiadationErrors(validationErrors);
        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(NotUniqueMailException.class)
    ResponseEntity<ApiError> handleUniqueMailEx(NotUniqueMailException exception){
        ApiError apiError = new ApiError();
        apiError.setPath("api/v1/user");
        apiError.setMessage(exception.getMessage());
        apiError.setStatusCode(400);
        apiError.setValiadationErrors(exception.getValidationErrors());
        return ResponseEntity.status(400).body(apiError);
    }

    @ExceptionHandler(ActivationNotificationException.class)
    ResponseEntity<ApiError> handleActivationNotificationException(ActivationNotificationException exception){
        ApiError apiError = new ApiError();
        apiError.setPath("api/v1/user");
        apiError.setMessage(exception.getMessage());
        apiError.setStatusCode(502);
        return ResponseEntity.status(502).body(apiError);
    }

    @ExceptionHandler(InvalidTokenException.class)
    ResponseEntity<ApiError> handleInvalidTokenException(InvalidTokenException exception){
        ApiError apiError = new ApiError();
        apiError.setPath("api/v1/user/activate");
        apiError.setMessage(exception.getMessage());
        apiError.setStatusCode(400);
        return ResponseEntity.status(400).body(apiError);
    }
}
