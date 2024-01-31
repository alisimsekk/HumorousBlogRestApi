package com.alisimsek.HumorousBlog.controller;

import com.alisimsek.HumorousBlog.entity.User;
import com.alisimsek.HumorousBlog.exception.ApiError;
import com.alisimsek.HumorousBlog.exception.NotUniqueMailException;
import com.alisimsek.HumorousBlog.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser (@Valid @RequestBody User user){
        User createdUser = userService.createUser(user);
        if (createdUser !=null){
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        }
        else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiError> handleMethodArgNotValidEx(MethodArgumentNotValidException exception){
        ApiError apiError = new ApiError();
        apiError.setPath("api/v1/user");
        apiError.setMessage("Validation error");
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
        apiError.setMessage("Validation error");
        apiError.setStatusCode(400);
        Map<String,String> validationErrors = new HashMap<>();
        validationErrors.put("mail","Mail in use");
        apiError.setValiadationErrors(validationErrors);
        return ResponseEntity.badRequest().body(apiError);
    }
}
