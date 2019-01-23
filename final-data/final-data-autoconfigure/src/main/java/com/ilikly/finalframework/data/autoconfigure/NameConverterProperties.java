package com.ilikly.finalframework.data.autoconfigure;

import com.ilikly.finalframework.data.mapping.converter.NameConverter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-09 19:24:08
 * @since 1.0
 */
@ConfigurationProperties(prefix = NameConverterProperties.NAME_CONVERTER_PROPERTIES)
@EnableConfigurationProperties(NameConverterProperties.class)
public class NameConverterProperties {
    static final String NAME_CONVERTER_PROPERTIES = "final.data.name-converter";
    private Class<? extends NameConverter> tableConverter;
    private Class<? extends NameConverter> columnConverter;

    public Class<? extends NameConverter> getTableConverter() {
        return tableConverter;
    }

    public void setTableConverter(Class<? extends NameConverter> tableConverter) {
        this.tableConverter = tableConverter;
    }

    public Class<? extends NameConverter> getColumnConverter() {
        return columnConverter;
    }

    public void setColumnConverter(Class<? extends NameConverter> columnConverter) {
        this.columnConverter = columnConverter;
    }
}
