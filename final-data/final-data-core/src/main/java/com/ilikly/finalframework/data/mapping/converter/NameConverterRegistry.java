package com.ilikly.finalframework.data.mapping.converter;

import com.ilikly.finalframework.core.configuration.FinalConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-09 17:52:29
 * @since 1.0
 */
public class NameConverterRegistry {
    private static final Logger logger = LoggerFactory.getLogger(NameConverterRegistry.class);
    private static NameConverterRegistry instance = new NameConverterRegistry();

    private final NameConverter defaultNameConverter = new SimpleNameConverter();


    private NameConverter tableNameConverter = defaultNameConverter;
    private NameConverter columnNameConverter = defaultNameConverter;

    private NameConverterRegistry() {
        final FinalConfiguration configuration = FinalConfiguration.getInstance();
        if (configuration.isConfiguration()) {

        }
    }

    public static NameConverterRegistry getInstance() {
        return instance;
    }

    public NameConverter getTableNameConverter() {
        return tableNameConverter;
    }

    public void setTableConverter(NameConverter tableNameConverter) {
        this.tableNameConverter = tableNameConverter;
    }

    public NameConverter getColumnNameConverter() {
        return columnNameConverter;
    }

    public void setColumnConverter(NameConverter columnNameConverter) {
        this.columnNameConverter = columnNameConverter;
    }
}
