package cn.com.likly.finalframework.spring.expression;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-28 09:51
 * @since 1.0
 */
public abstract class Spel {

    public static Object getValue(String expression, Object root) {
        return new SpelParser(root).getValue(expression);
    }

    public static Boolean getBoolean(String expression, Object root) {
        return getBoolean(expression, root, false);
    }

    public static Boolean getBoolean(String expression, Object root, Boolean defVal) {
        return new SpelParser(root).getBoolean(expression, defVal);
    }

}
