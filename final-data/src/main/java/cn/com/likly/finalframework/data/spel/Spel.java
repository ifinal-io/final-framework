package cn.com.likly.finalframework.data.spel;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-01 14:29
 * @since 1.0
 */
public interface Spel {


    static Object getValue(String el, Object context) {

        return null;
    }

    static String getString(String el, Object context) {
        return SpelParser.getString(el, context);
    }

    static boolean getBoolean(String el, Object context) {
        return SpelParser.getBoolean(el, context);
    }

    static long getLong(String el, Object context) {
        return SpelParser.getLong(el, context);
    }


}
