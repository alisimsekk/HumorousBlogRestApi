package com.alisimsek.HumorousBlog.dto.response;

import com.alisimsek.HumorousBlog.security.Token;
import lombok.Data;

@Data
public class AuthResponse {

    private UserResponse userResponse;
    private Token token;

}
