package org.ifinal.finalframework.dashboard.ui.autoconfiguration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.ifinal.finalframework.auto.spring.factory.annotation.SpringAutoConfiguration;
import org.ifinal.finalframework.dashboard.ui.dialect.JsonDialect;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
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

