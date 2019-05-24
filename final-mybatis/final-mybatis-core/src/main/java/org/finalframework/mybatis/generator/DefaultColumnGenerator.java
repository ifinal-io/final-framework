package org.finalframework.mybatis.generator;

import org.apache.ibatis.type.TypeHandler;
import org.finalframework.core.Assert;
import org.finalframework.data.annotation.FunctionColumn;
import org.finalframework.data.mapping.Property;
import org.finalframework.data.mapping.generator.AbsColumnGenerator;
import org.finalframework.mybatis.Utils;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-22 12:15:32
 * @since 1.0
 */
public class DefaultColumnGenerator extends AbsColumnGenerator {

    public static final DefaultColumnGenerator INSTANCE = new DefaultColumnGenerator();

    @Override
    public String generateWriteValue(@Nullable Property referenceProperty, Property property, String value) {
        if (property.hasAnnotation(FunctionColumn.class)) {
            FunctionColumn functionColumn = property.getRequiredAnnotation(FunctionColumn.class);
            String writer = functionColumn.writer();
            return String.format(writer, formatValue(referenceProperty, property, value));
        } else {
            return formatValue(referenceProperty, property, value);
        }

    }

    protected String formatValue(@Nullable Property referenceProperty, Property property, String value) {
        final Class javaType = Utils.getPropertyJavaType(property);
        final Class<? extends TypeHandler> typeHandler = Utils.getPropertyTypeHandlerClass(property);
        final StringBuilder builder = new StringBuilder();

        builder.append(property.placeholder() ? "#{" : "${").append(value);

        if (Assert.nonNull(referenceProperty)) {
            builder.append(".").append(referenceProperty.getName());
        }
        builder.append(".").append(property.getName());

        if (typeHandler != null) {
            builder.append(",javaType=").append(javaType.getCanonicalName());
            builder.append(",typeHandler=").append(typeHandler.getCanonicalName());
        }
        builder.append("}");

        return builder.toString();
    }

}
