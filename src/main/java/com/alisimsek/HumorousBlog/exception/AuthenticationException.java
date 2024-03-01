package com.alisimsek.HumorousBlog.exception;

import com.alisimsek.HumorousBlog.shared.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

public class AuthenticationException extends RuntimeException{
    public AuthenticationException() {
        super(Messages.getMessageForLocale("humorous.auth.invalid.credentials", LocaleContextHolder.getLocale()));
    }
}
