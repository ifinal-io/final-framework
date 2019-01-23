package com.ilikly.finalframework.data.autoconfigure;

import com.ilikly.finalframework.data.mapping.generator.ColumnGenerator;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-22 14:51:45
 * @since 1.0
 */
@ConfigurationProperties(prefix = ColumnGeneratorProperties.COLUMN_GENERATOR_PROPERTIES)
@EnableConfigurationProperties(ColumnGeneratorProperties.class)
public class ColumnGeneratorProperties {
    static final String COLUMN_GENERATOR_PROPERTIES = "final.data.column-generator";

    private Class<? extends ColumnGenerator> defaultColumnGenerator;

    public Class<? extends ColumnGenerator> getDefaultColumnGenerator() {
        return defaultColumnGenerator;
    }

    public void setDefaultColumnGenerator(Class<? extends ColumnGenerator> defaultColumnGenerator) {
        this.defaultColumnGenerator = defaultColumnGenerator;
    }

}
