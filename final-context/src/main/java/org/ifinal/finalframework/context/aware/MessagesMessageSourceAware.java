package org.ifinal.finalframework.context.aware;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import org.ifinal.finalframework.context.util.Messages;

import lombok.extern.slf4j.Slf4j;

/**
 * An aware helper to receive {@link MessageSource} from {@link MessageSourceAware} and set to {@link Messages}.
 *
 * @author likly
 * @version 1.0.0
 * @see MessageSource
 * @see Messages
 * @since 1.0.0
 */
@Slf4j
@Component
public class MessagesMessageSourceAware implements MessageSourceAware {

    @Override
    public void setMessageSource(final @NonNull MessageSource messageSource) {
        if (logger.isInfoEnabled()) {
            logger.info("set message source: {}", messageSource.getClass());
        }
        Messages.setMessageSource(messageSource);
    }

}
