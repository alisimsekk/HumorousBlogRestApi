package com.alisimsek.HumorousBlog.exception;

import com.alisimsek.HumorousBlog.shared.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

public class ActivationNotificationException extends RuntimeException  {

    public ActivationNotificationException(){
        super(Messages.getMessageForLocale("humorous.create.user.mail.failure", LocaleContextHolder.getLocale()));
    }

}
