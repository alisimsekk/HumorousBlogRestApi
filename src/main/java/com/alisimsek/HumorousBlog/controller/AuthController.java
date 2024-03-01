package com.alisimsek.HumorousBlog.controller;

import com.alisimsek.HumorousBlog.dto.request.Credentials;
import com.alisimsek.HumorousBlog.dto.response.AuthResponse;
import com.alisimsek.HumorousBlog.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<AuthResponse> handleAuthentication(@Valid @RequestBody Credentials cred){
        return ResponseEntity.ok().body(authService.authenticate(cred));
    }
}
