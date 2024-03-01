package com.alisimsek.HumorousBlog.service;

import com.alisimsek.HumorousBlog.dto.request.Credentials;
import com.alisimsek.HumorousBlog.dto.response.AuthResponse;
import com.alisimsek.HumorousBlog.dto.response.UserResponse;
import com.alisimsek.HumorousBlog.entity.User;
import com.alisimsek.HumorousBlog.exception.AuthenticationException;
import com.alisimsek.HumorousBlog.security.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthResponse authenticate(Credentials cred){
        Optional<User> userFromDb = userService.findUserByMail(cred.mail());
        if (userFromDb.isEmpty()){
            throw new AuthenticationException();
        }
        if (!passwordEncoder.matches(cred.password(),userFromDb.get().getPassword())){
            throw new AuthenticationException();
        }

        Token token = tokenService.createToken(userFromDb.get(), cred);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(token);
        authResponse.setUserResponse(new UserResponse(userFromDb.get()));
        return authResponse;
    }
}
