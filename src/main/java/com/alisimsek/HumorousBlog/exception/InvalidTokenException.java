package com.alisimsek.HumorousBlog.exception;

import com.alisimsek.HumorousBlog.shared.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

public class InvalidTokenException extends RuntimeException{
    public InvalidTokenException() {
        super(Messages.getMessageForLocale("humorous.activate.user.invalid.token", LocaleContextHolder.getLocale()));
    }
}
