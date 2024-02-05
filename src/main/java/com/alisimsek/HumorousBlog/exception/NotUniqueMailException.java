package com.alisimsek.HumorousBlog.exception;

import com.alisimsek.HumorousBlog.shared.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Collections;
import java.util.Map;

public class NotUniqueMailException extends RuntimeException{
    public NotUniqueMailException() {
        super(Messages.getMessageForLocale("humorous.error.validation", LocaleContextHolder.getLocale()));
    }

    public Map<String, String> getValidationErrors(){
        return Collections.singletonMap("mail", Messages.getMessageForLocale("humorous.constraint.mail.notunique", LocaleContextHolder.getLocale()));
    }
}
