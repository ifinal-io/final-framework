/*
 * Copyright 2020-2021 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ifinalframework.context.util;

import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Objects;

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
            final String message = messageSource.getMessage(code, args, null, LocaleContextHolder.getLocale());
            if (Objects.isNull(message)) {
                return MessageFormatter.arrayFormat(code, args).getMessage();
            }
            return message;
        } catch (Exception e) {
            logger.warn("get message error:{}", code, e);
            return code;
        }
    }

}

