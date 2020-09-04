/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.finalframework.data.util;


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

