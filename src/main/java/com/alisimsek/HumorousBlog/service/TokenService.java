package com.alisimsek.HumorousBlog.service;

import com.alisimsek.HumorousBlog.dto.request.Credentials;
import com.alisimsek.HumorousBlog.entity.User;
import com.alisimsek.HumorousBlog.security.Token;

public interface TokenService {

    Token createToken(User user, Credentials cred);

    User verifyToken(String authorizationHeader);

}
