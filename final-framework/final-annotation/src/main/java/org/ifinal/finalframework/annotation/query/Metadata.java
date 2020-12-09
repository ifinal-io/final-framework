package org.ifinal.finalframework.annotation.query;


import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 * @author likly
 * @version 1.0.0
 * @see CriterionSqlProvider
 * @see Criterion
 * @since 1.0.0
 */
public class Metadata extends LinkedHashMap<String, Object> implements Serializable {

    public static final String ATTRIBUTE_NAME_AND_OR = "andOr";
    public static final String ATTRIBUTE_NAME_PROPERTY = "property";
    public static final String ATTRIBUTE_NAME_COLUMN = "column";
    public static final String ATTRIBUTE_NAME_VALUE = "value";
    public static final String ATTRIBUTE_NAME_JAVA_TYPE = "javaType";
    public static final String ATTRIBUTE_NAME_TYPE_HANDLER = "typeHandler";
    public static final String ATTRIBUTE_NAME_QUERY = "query";


}

