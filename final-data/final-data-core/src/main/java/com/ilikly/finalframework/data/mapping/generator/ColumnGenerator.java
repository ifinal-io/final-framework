package com.ilikly.finalframework.data.mapping.generator;

import com.ilikly.finalframework.data.mapping.Property;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-21 20:21:20
 * @since 1.0
 */
public interface ColumnGenerator {

    String generateReadColumn(String table, String prefix, Property property);

    String generateWriteColumn(String table, String prefix, Property property);

    String generateWriteValue(String prefix, Property property, String value);
}
