package org.ifinal.finalframework.context.util;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;

import org.ifinal.finalframework.ContextApplicationContext;

import java.util.Locale;

import org.junit.jupiter.api.Test;

/**
 * MessagesTest.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
class MessagesTest {

    @Test
    void getMessage() {

        ConfigurableApplicationContext context = SpringApplication.run(ContextApplicationContext.class);
        System.out.println(context.getMessage("success", null, LocaleContextHolder.getLocale()));
        System.out.println(context.getMessage("success", null, Locale.ENGLISH));
        System.out.println(context.getMessage("success2", null, Locale.JAPANESE));
        System.out.println(Messages.getMessage("success2"));

    }

}
