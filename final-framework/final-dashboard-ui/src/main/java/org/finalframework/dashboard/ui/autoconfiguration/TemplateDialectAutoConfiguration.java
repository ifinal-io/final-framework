package org.finalframework.dashboard.ui.autoconfiguration;


import org.finalframework.auto.spring.factory.annotation.SpringAutoConfiguration;
import org.finalframework.dashboard.ui.dialect.JsonDialect;
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
@SpringAutoConfiguration
public class TemplateDialectAutoConfiguration {
    @Resource
    private TemplateEngine templateEngine;


    @PostConstruct
    public void init() {
        templateEngine.addDialect(new JsonDialect());
    }
}

