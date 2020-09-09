

package org.finalframework.annotation.query;

/**
 * @author likly
 * @version 1.0
 * @date 2020-07-18 01:19:45
 * @since 1.0
 */
public interface Constants {
    String PREFIX = "<![CDATA[";
    String SUFFIX = "]]>";
    String AND_OR = "${andOr} ";
    String COLUMN = "${column}";
    String VALUE = "${value}";
    String MIN = "${value}.min";
    String MAX = "${value}.max";
    String JAVA_TYPE = "#if($javaType),javaType=$!{javaType.canonicalName}#end";
    String TYPE_HANDLER = "#if($typeHandler),typeHandler=$!{typeHandler.canonicalName}#end";
    String DEFAULT_VALUE = "#{" + VALUE + JAVA_TYPE + TYPE_HANDLER + "}";
    String DEFAULT_MIN = "#{" + MIN + JAVA_TYPE + TYPE_HANDLER + "}";
    String DEFAULT_MAX = "#{" + MAX + JAVA_TYPE + TYPE_HANDLER + "}";
    String EQUAL = PREFIX + AND_OR + COLUMN + " = " + DEFAULT_VALUE + SUFFIX;
    String NOT_EQUAL = PREFIX + AND_OR + COLUMN + " != " + DEFAULT_VALUE + SUFFIX;

}
