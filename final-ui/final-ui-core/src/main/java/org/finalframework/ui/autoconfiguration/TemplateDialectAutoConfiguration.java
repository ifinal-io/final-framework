package org.finalframework.ui.autoconfiguration;


import org.finalframework.auto.spring.factory.annotation.SpringConfiguration;
import org.finalframework.ui.dialect.JsonDialect;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-09 16:12:09
 * @since 1.0
 */
@Configuration
@SpringConfiguration
public class TemplateDialectAutoConfiguration {
    @Resource
    private TemplateEngine templateEngine;


    @PostConstruct
    public void init() {
        templateEngine.addDialect(new JsonDialect());
    }
}

