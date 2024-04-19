package com.alisimsek.HumorousBlog.controller;

import com.alisimsek.HumorousBlog.configuration.CurrentUser;
import com.alisimsek.HumorousBlog.dto.request.UserCreate;
import com.alisimsek.HumorousBlog.dto.request.UserUpdate;
import com.alisimsek.HumorousBlog.dto.response.UserResponse;
import com.alisimsek.HumorousBlog.entity.User;
import com.alisimsek.HumorousBlog.service.UserService;
import com.alisimsek.HumorousBlog.shared.GenericMessage;
import com.alisimsek.HumorousBlog.shared.Messages;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public Page<UserResponse> findAllUsers(Pageable page, @AuthenticationPrincipal CurrentUser currentUser){
        return userService.findAllUsers(page, currentUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findByUser(@PathVariable Long id){
        return new ResponseEntity<>(userService.findByUser(id),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createUser (@Valid @RequestBody UserCreate userCreate){
        System.err.println("----" + LocaleContextHolder.getLocale().getLanguage());
        User createdUser = userService.createUser(userCreate);
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

    @PutMapping("/{id}")
    @PreAuthorize("#id == principal.id")
    public ResponseEntity<UserResponse> update (
            @PathVariable Long id,
            @Valid @RequestBody UserUpdate userUpdate){
        return new ResponseEntity<>(userService.update(id,userUpdate),HttpStatus.OK);
    }
}
