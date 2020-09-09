

package org.finalframework.context.util;


import org.finalframework.auto.spring.factory.annotation.SpringFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author likly
 * @version 1.0
 * @date 2020-01-09 18:33:29
 * @since 1.0
 */
@SpringFactory(Component.class)
public class Messages {
    private static final Logger logger = LoggerFactory.getLogger(Messages.class);
    private static MessageSource messageSource;

    public Messages(MessageSource messageSource) {
        Messages.messageSource = messageSource;
    }

    public static String getMessage(String code, Object... args) {
        return getMessage(code, null, args);
    }

    public static String getMessage(String code, String defaultMessage, Object... args) {
        if (messageSource == null) {
            return defaultMessage != null ? defaultMessage : MessageFormatter.arrayFormat(code, args).getMessage();
        }
        try {
            return messageSource.getMessage(code, args, defaultMessage, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            logger.error("get message error:{}", code, e);
            return code;
        }
    }
}

