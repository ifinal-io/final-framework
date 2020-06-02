package org.finalframework.data.query.criterion;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Properties;
import org.finalframework.core.parser.xml.PropertyParser;
import org.finalframework.data.query.QProperty;
import org.finalframework.data.query.criterion.function.CriterionFunction;
import org.springframework.lang.NonNull;

/**
 * @author sli
 * @version 1.0
 * @date 2020-06-01 21:00:52
 * @since 1.0
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
