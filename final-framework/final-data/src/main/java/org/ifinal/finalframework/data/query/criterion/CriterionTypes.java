package org.ifinal.finalframework.data.query.criterion;

import org.ifinal.finalframework.core.parser.xml.PropertyParser;
import org.ifinal.finalframework.data.query.QProperty;
import org.ifinal.finalframework.data.query.criterion.function.CriterionFunction;
import org.springframework.lang.NonNull;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Properties;

/**
 * @author sli
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class CriterionTypes {

    public static boolean isProperty(@NonNull Object value) {
        return value instanceof QProperty;
    }

    public static boolean isFunction(@NonNull Object value) {
        return value instanceof CriterionFunction;
    }

    public static boolean isCollection(@NonNull Object value) {
        return value instanceof Collection;
    }

    public static boolean isArray(@NonNull Object value) {
        return value instanceof Array;
    }

    public static boolean isValue(@NonNull Object value) {
        return value instanceof CriterionValue;
    }

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("value", "criterion.value");
        System.out.println(PropertyParser.parse("\\${${value}.column}", properties));
    }


}
