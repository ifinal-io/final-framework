package com.ilikly.finalframework.data.autoconfigure;

import com.ilikly.finalframework.spring.coding.ApplicationEventListener;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-22 16:12:08
 * @since 1.0
 */
@ApplicationEventListener
public class ColumnGeneratorConfigurerApplicationListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
    private static final String DEFAULT_COLUMN_GENERATOR = "final.data.column-generator.default-column-generator";
    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        ConfigurableEnvironment environment = event.getEnvironment();
    }
}
