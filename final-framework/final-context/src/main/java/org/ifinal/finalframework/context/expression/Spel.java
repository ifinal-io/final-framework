package org.ifinal.finalframework.context.expression;

import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public final class Spel {

    private static final ExpressionParser PARSER = new SpelExpressionParser(new SpelParserConfiguration(true,true));

    private Spel() {
    }

    private static EvaluationContext wrapContext(Object context) {
        if (context instanceof EvaluationContext) {
            return (EvaluationContext) context;
        }

        StandardEvaluationContext evaluationContext = new StandardEvaluationContext(context);

        evaluationContext.addPropertyAccessor(new MapAccessor());

        return evaluationContext;

    }

    public static Object getValue(final String expression, final Object context) {
        return PARSER.parseExpression(expression).getValue(wrapContext(context));
    }

    public static <T> T getValue(final String expression, final Object context, final Class<T> type) {
        return PARSER.parseExpression(expression).getValue(wrapContext(context), type);
    }

    public static void setValue(final String expression, final Object context, final Object value) {
        if(context instanceof EvaluationContext){
            PARSER.parseExpression(expression).setValue((EvaluationContext) context, value);
        }else {
            PARSER.parseExpression(expression).setValue(context, value);
        }
    }

    public static Expression expression(final String expression,final ParserContext context){
        return PARSER.parseExpression(expression,context);
    }

    public static Expression expression(final String expression) {
        return PARSER.parseExpression(expression);
    }

}

