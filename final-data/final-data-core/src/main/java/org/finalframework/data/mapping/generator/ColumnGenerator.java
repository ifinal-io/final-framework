package org.finalframework.data.mapping.generator;

import org.finalframework.data.mapping.Property;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-21 20:21:20
 * @since 1.0
 */
public interface ColumnGenerator {

    String generateReadColumn(String table, @Nullable Property referenceProperty, @NonNull Property property);

    String generateWriteColumn(String table, @Nullable Property referenceProperty, Property property);

    String generateWriteValue(@Nullable Property referenceProperty, Property property, String value);
}
