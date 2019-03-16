package org.finalframework.data.mapping.generator;

import org.finalframework.data.annotation.FunctionColumn;
import org.finalframework.data.annotation.enums.ReferenceMode;
import org.finalframework.data.mapping.Property;
import org.finalframework.data.mapping.converter.NameConverterRegistry;

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

    protected String getColumn(Property referenceProperty, Property property) {

        String column;

        if (referenceProperty == null) {
            column = property.getColumn();
        } else {
            final String referenceColumn = referenceProperty.referenceColumn(property.getName()) != null ?
                    referenceProperty.referenceColumn(property.getName()) : property.getColumn();

            column = property.isIdProperty() && referenceProperty.referenceMode() == ReferenceMode.SIMPLE ?
                    referenceProperty.getColumn() : referenceProperty.getColumn() + referenceColumn.substring(0, 1).toUpperCase() + referenceColumn.substring(1);
        }

        return NameConverterRegistry.getInstance().getTableNameConverter().convert(column);
    }

    @Override
    public String generateReadColumn(String table, Property referenceProperty, Property property) {
        final String column = getColumn(referenceProperty, property);
        if (property.hasAnnotation(FunctionColumn.class)) {
            FunctionColumn functionColumn = property.getRequiredAnnotation(FunctionColumn.class);
            return String.format(functionColumn.reader(), formatColumn(column)) + " AS " + column;
        } else {
            return formatColumn(column);
        }

    }

    @Override
    public String generateWriteColumn(String table, Property referenceProperty, Property property) {
        return formatColumn(getColumn(referenceProperty, property));
    }

}
