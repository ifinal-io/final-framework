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

    private static final ExpressionParser PARSER = new SpelExpressionParser(new SpelParserConfiguration(true, true));

    private static final MapAccessor MAP_ACCESSOR = new MapAccessor();

    private static final ParserContext PARSER_CONTEXT = ParserContext.TEMPLATE_EXPRESSION;

    private Spel() {
        throw new IllegalAccessError("Spel is not support new instance for you!");
    }

    private static EvaluationContext wrapContext(Object context) {
        if (context instanceof EvaluationContext) {
            return (EvaluationContext) context;
        }

        StandardEvaluationContext evaluationContext = new StandardEvaluationContext(context);
        evaluationContext.addPropertyAccessor(MAP_ACCESSOR);
        return evaluationContext;

    }

    public static Object getValue(final String expression) {
        return expression(expression, PARSER_CONTEXT).getValue();
    }

    public static Object getValue(final String expression, final Object context) {
        return expression(expression, PARSER_CONTEXT).getValue(wrapContext(context));
    }

    public static <T> T getValue(final String expression, final Object context, final Class<T> type) {
        return expression(expression, PARSER_CONTEXT).getValue(wrapContext(context), type);
    }

    public static void setValue(final String expression, final Object context, final Object value) {
        if (context instanceof EvaluationContext) {
            PARSER.parseExpression(expression).setValue((EvaluationContext) context, value);
        } else {
            PARSER.parseExpression(expression).setValue(context, value);
        }
    }

    private static Expression expression(final String expression, final ParserContext context) {
        return PARSER.parseExpression(expression, context);
    }

}

