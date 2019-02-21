package com.ilikly.finalframework.data.mapping.generator;

import com.ilikly.finalframework.core.Assert;
import com.ilikly.finalframework.data.annotation.FunctionColumn;
import com.ilikly.finalframework.data.mapping.Property;
import com.ilikly.finalframework.data.mapping.converter.NameConverterRegistry;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-21 22:20:37
 * @since 1.0
 */
@SuppressWarnings("all")
public abstract class AbsColumnGenerator implements ColumnGenerator {
    private static final Set<String> SQL_KEYS = new HashSet<>(Arrays.asList(
            "key".toUpperCase()
    ));

    protected String formatColumn(String column) {
        return SQL_KEYS.contains(column.toUpperCase()) ? String.format("`%s`", column) : column;
    }

    protected String getColumn(String prefix, Property property) {
        String column = Assert.isEmpty(prefix) ? property.getColumn() : property.isIdProperty() ? prefix :
                prefix + property.getColumn().substring(0, 1).toUpperCase() + property.getColumn().substring(1);
        return NameConverterRegistry.getInstance().getTableNameConverter().convert(column);
    }

    @Override
    public String generateReadColumn(String table, String prefix, Property property) {
        final String column = getColumn(prefix, property);
        if (property.hasAnnotation(FunctionColumn.class)) {
            FunctionColumn functionColumn = property.getRequiredAnnotation(FunctionColumn.class);
            return String.format(functionColumn.reader(), formatColumn(column)) + " AS " + getColumn(prefix, property);
        } else {
            return formatColumn(column);
        }

    }

    @Override
    public String generateWriteColumn(String table, String prefix, Property property) {
        return formatColumn(getColumn(prefix, property));
    }

}
