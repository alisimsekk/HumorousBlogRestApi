package com.alisimsek.HumorousBlog.exception;

public class AuthorizationException extends  RuntimeException{
    public AuthorizationException() {
        super("Forbidden");
    }
}
