package com.alisimsek.HumorousBlog.controller;

import com.alisimsek.HumorousBlog.dto.request.UserCreateDto;
import com.alisimsek.HumorousBlog.dto.response.UserResponse;
import com.alisimsek.HumorousBlog.entity.User;
import com.alisimsek.HumorousBlog.exception.*;
import com.alisimsek.HumorousBlog.service.UserService;
import com.alisimsek.HumorousBlog.shared.GenericMessage;
import com.alisimsek.HumorousBlog.shared.Messages;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public Page<UserResponse> findAllUsers(Pageable page){
        return userService.findAllUsers(page);
    }

   /* Projection
   @GetMapping
    public Page<UserProjection> getAllUsers(Pageable page){
        return userService.getAllUsers(page);
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findByUser(@PathVariable Long id){
        return new ResponseEntity<>(userService.findByUser(id),HttpStatus.OK);
    }

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
    ResponseEntity<ApiError> handleMethodArgNotValidEx(MethodArgumentNotValidException exception,WebRequest request){
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
    ResponseEntity<ApiError> handleUniqueMailEx(NotUniqueMailException exception,WebRequest request){
        ApiError apiError = new ApiError();
        apiError.setPath(request.getDescription(false));
        apiError.setMessage(exception.getMessage());
        apiError.setStatusCode(400);
        apiError.setValidationErrors(exception.getValidationErrors());
        return ResponseEntity.status(400).body(apiError);
    }

    @ExceptionHandler(ActivationNotificationException.class)
    ResponseEntity<ApiError> handleActivationNotificationException(ActivationNotificationException exception,WebRequest request){
        ApiError apiError = new ApiError();
        apiError.setPath(request.getDescription(false));
        apiError.setMessage(exception.getMessage());
        apiError.setStatusCode(502);
        return ResponseEntity.status(502).body(apiError);
    }

    @ExceptionHandler(InvalidTokenException.class)
    ResponseEntity<ApiError> handleInvalidTokenException(InvalidTokenException exception,WebRequest request){
        ApiError apiError = new ApiError();
        apiError.setPath(request.getDescription(false));
        apiError.setMessage(exception.getMessage());
        apiError.setStatusCode(400);
        return ResponseEntity.status(400).body(apiError);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    ResponseEntity<ApiError> handleEntityNotFoundException(EntityNotFoundException exception, HttpServletRequest request){
        ApiError apiError = new ApiError();
        apiError.setPath(request.getRequestURI());
        apiError.setMessage(exception.getMessage());
        apiError.setStatusCode(404);
        return ResponseEntity.status(404).body(apiError);
    }
}
