package com.ilikly.finalframework.data.spel;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-31 17:30
 * @since 1.0
 */
public class SpelParser {

    private final static ExpressionParser parser = new SpelExpressionParser();

    public static Object get(String el, Object context) {
        return parser.parseExpression(el).getValue(new StandardEvaluationContext(context));
    }

    public static String getString(String el, Object context) {
        return parser.parseExpression(el).getValue(new StandardEvaluationContext(context), String.class);
    }

    public static boolean getBoolean(String el, Object context) {
        return parser.parseExpression(el).getValue(new StandardEvaluationContext(context), boolean.class);
    }

    public static long getLong(String el, Object context) {
        return parser.parseExpression(el).getValue(new StandardEvaluationContext(context), long.class);
    }
}
