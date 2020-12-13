package org.ifinal.finalframework.context.util;


import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public final class Messages {

    private static final Logger logger = LoggerFactory.getLogger(Messages.class);

    @Setter
    private static MessageSource messageSource;

    private Messages() {
    }


    public static String getMessage(final String code, final Object... args) {

        return getMessage(code, null, args);
    }

    public static String getMessage(final String code, final String defaultMessage, final Object... args) {

        if (messageSource == null) {
            return defaultMessage != null ? defaultMessage : MessageFormatter.arrayFormat(code, args).getMessage();
        }
        try {
            return messageSource.getMessage(code, args, defaultMessage, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            logger.warn("get message error:{}", code, e);
            return code;
        }
    }

}

