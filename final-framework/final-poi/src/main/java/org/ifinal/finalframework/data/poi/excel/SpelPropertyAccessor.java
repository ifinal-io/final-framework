package org.ifinal.finalframework.data.poi.excel;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * SpelPropertyAccessor.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class SpelPropertyAccessor implements PropertyAccessor<Object, Object> {

    private final ExpressionParser expressionParser;

    public SpelPropertyAccessor() {
        this(new SpelExpressionParser(new SpelParserConfiguration(true,true)));
    }

    public SpelPropertyAccessor(final ExpressionParser expressionParser) {
        this.expressionParser = expressionParser;
    }

    @Nullable
    @Override
    public Object read(@NonNull final String expression, @Nullable final Object context) {

        if(context instanceof EvaluationContext){
            return expressionParser.parseExpression(expression, ParserContext.TEMPLATE_EXPRESSION)
                .getValue((EvaluationContext) context);
        }

        return expressionParser.parseExpression(expression, ParserContext.TEMPLATE_EXPRESSION).getValue(context);
    }

}
