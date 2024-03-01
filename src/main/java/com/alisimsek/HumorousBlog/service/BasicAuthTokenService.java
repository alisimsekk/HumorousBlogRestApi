package com.alisimsek.HumorousBlog.service;

import com.alisimsek.HumorousBlog.dto.request.Credentials;
import com.alisimsek.HumorousBlog.entity.User;
import com.alisimsek.HumorousBlog.security.Token;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class BasicAuthTokenService implements TokenService{
    @Override
    public Token createToken(User user, Credentials cred) {
        String mailColonPassword = cred.mail()+":"+cred.password();
        String token = Base64.getEncoder().encodeToString(mailColonPassword.getBytes());
        return new Token("Basic", token);
    }

    @Override
    public User verifyToken(String authorizationHeader) {
        return null;
    }
}
