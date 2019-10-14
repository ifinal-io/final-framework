package org.finalframework.data.mapping.converter;

import org.finalframework.core.Assert;
import org.finalframework.core.configuration.Configuration;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-09 17:52:29
 * @since 1.0
 */
public class NameConverterRegistry {

    private static final String FINAL_NAME_CONVERTER_TABLE_CONVERTER = "final.nameConverter.tableConverter";
    private static final String FINAL_NAME_CONVERTER_COLUMN_CONVERTER = "final.nameConverter.columnConverter";

    private static NameConverterRegistry instance = new NameConverterRegistry();

    private final NameConverter defaultNameConverter = new SimpleNameConverter();

    private NameConverter tableNameConverter;//= defaultNameConverter;
    private NameConverter columnNameConverter;// = defaultNameConverter;

    private NameConverterRegistry() {
        final Configuration configuration = Configuration.getInstance();
        initTableConverter(configuration.getString(FINAL_NAME_CONVERTER_TABLE_CONVERTER, null));
        initNameConverter(configuration.getString(FINAL_NAME_CONVERTER_COLUMN_CONVERTER, null));

    }

    private void initTableConverter(@Nullable String tableNameConverter) {
        if (Assert.isEmpty(tableNameConverter)) return;
        try {
            this.tableNameConverter = (NameConverter) Class.forName(tableNameConverter).newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }


    private void initNameConverter(@Nullable String columnNameConverter) {
        if (Assert.isEmpty(columnNameConverter)) return;
        try {
            this.columnNameConverter = (NameConverter) Class.forName(columnNameConverter).newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
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
