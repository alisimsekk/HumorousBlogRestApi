package com.alisimsek.HumorousBlog.exception;

import com.alisimsek.HumorousBlog.shared.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(Long id, Class entityClass) {
        super(Messages.getMessageForLocale("humorous.notfound.message", LocaleContextHolder.getLocale(), id, entityClass.getSimpleName()));
    }
}
