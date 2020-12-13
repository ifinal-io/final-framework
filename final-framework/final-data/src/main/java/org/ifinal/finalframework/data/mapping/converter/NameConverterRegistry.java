package org.ifinal.finalframework.data.mapping.converter;

import org.ifinal.finalframework.Configuration;
import org.ifinal.finalframework.annotation.data.NameConverter;
import org.ifinal.finalframework.util.Asserts;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public final class NameConverterRegistry {

    private static final String FINAL_NAME_CONVERTER_TABLE_CONVERTER = "final.data.name-converter.table-converter";

    private static final String FINAL_NAME_CONVERTER_COLUMN_CONVERTER = "final.data.name-converter.column-converter";

    private static final NameConverterRegistry instance = new NameConverterRegistry();

    private final NameConverter defaultNameConverter = new CameHump2UnderlineNameConverter();

    private NameConverter tableNameConverter = defaultNameConverter;

    private NameConverter columnNameConverter = defaultNameConverter;

    private NameConverterRegistry() {
        reload();
    }

    public static NameConverterRegistry getInstance() {
        return instance;
    }

    public void reload() {
        final Configuration configuration = Configuration.getInstance();
        initTableConverter(configuration.getString(FINAL_NAME_CONVERTER_TABLE_CONVERTER, null));
        initNameConverter(configuration.getString(FINAL_NAME_CONVERTER_COLUMN_CONVERTER, null));
    }

    private void initTableConverter(final @Nullable String tableNameConverter) {

        if (Asserts.isEmpty(tableNameConverter)) {
            return;
        }
        try {
            this.tableNameConverter = (NameConverter) Class.forName(tableNameConverter).getConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    private void initNameConverter(final @Nullable String columnNameConverter) {

        if (Asserts.isEmpty(columnNameConverter)) {
            return;
        }
        try {
            this.columnNameConverter = (NameConverter) Class.forName(columnNameConverter).getConstructor()
                .newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public NameConverter getTableNameConverter() {
        return tableNameConverter;
    }

    public void setTableConverter(final NameConverter tableNameConverter) {

        this.tableNameConverter = tableNameConverter;
    }

    public NameConverter getColumnNameConverter() {
        return columnNameConverter;
    }

    public void setColumnConverter(final NameConverter columnNameConverter) {

        this.columnNameConverter = columnNameConverter;
    }

}
