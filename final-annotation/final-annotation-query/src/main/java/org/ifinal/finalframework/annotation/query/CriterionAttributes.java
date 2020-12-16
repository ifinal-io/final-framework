package org.ifinal.finalframework.annotation.query;

import org.springframework.core.annotation.AnnotationAttributes;

/**
 * @author likly
 * @version 1.0.0
 * @see CriterionSqlProvider
 * @see Criterion
 * @since 1.0.0
 */
public class CriterionAttributes extends AnnotationAttributes {

    public static final String ATTRIBUTE_NAME_AND_OR = "andOr";

    public static final String ATTRIBUTE_NAME_PROPERTY = "property";

    public static final String ATTRIBUTE_NAME_COLUMN = "column";

    public static final String ATTRIBUTE_NAME_VALUE = "value";

    public static final String ATTRIBUTE_NAME_JAVA_TYPE = "javaType";

    public static final String ATTRIBUTE_NAME_TYPE_HANDLER = "typeHandler";

    public static final String ATTRIBUTE_NAME_QUERY = "query";

    public AndOr getAndor() {
        return getEnum(ATTRIBUTE_NAME_AND_OR);
    }

    public String getProperty() {
        return getString(ATTRIBUTE_NAME_PROPERTY);
    }

    public String getColumn() {
        return getString(ATTRIBUTE_NAME_COLUMN);
    }

    public Class<?> getJavaType() {
        return getClass(ATTRIBUTE_NAME_JAVA_TYPE);
    }

    public Class<?> getTypeHandler() {
        return getClass(ATTRIBUTE_NAME_TYPE_HANDLER);
    }

}

