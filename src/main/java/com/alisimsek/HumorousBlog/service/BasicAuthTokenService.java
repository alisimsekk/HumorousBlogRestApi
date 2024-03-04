package com.alisimsek.HumorousBlog.service;

import com.alisimsek.HumorousBlog.dto.request.Credentials;
import com.alisimsek.HumorousBlog.entity.User;
import com.alisimsek.HumorousBlog.security.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BasicAuthTokenService implements TokenService{

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public Token createToken(User user, Credentials cred) {
        String mailColonPassword = cred.mail()+":"+cred.password();
        String token = Base64.getEncoder().encodeToString(mailColonPassword.getBytes());
        return new Token("Basic", token);
    }

    @Override
    public User verifyToken(String authorizationHeader) {
        if (authorizationHeader == null) return null;
        var base64Encoded = authorizationHeader.split("Basic ")[1];
        var decoded = new String (Base64.getDecoder().decode(base64Encoded));
        var credentials = decoded.split(":");
        var mail = credentials[0];
        var password = credentials[1];
        Optional<User> userFromDb = userService.findUserByMail(mail);
        if (userFromDb.isEmpty()) return null;
        if ( !passwordEncoder.matches(password, userFromDb.get().getPassword())) return null;
        return userFromDb.get();
    }
}
