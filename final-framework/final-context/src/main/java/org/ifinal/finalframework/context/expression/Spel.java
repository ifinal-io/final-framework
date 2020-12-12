package org.ifinal.finalframework.context.expression;


import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public final class Spel {

    private final SpelExpressionParser parser;

    public Spel() {
        this(new SpelExpressionParser());
    }

    public Spel(final SpelExpressionParser parser) {

        this.parser = parser;
    }

    public Expression expression(final String expression) {

        return this.parser.parseExpression(expression);
    }

}

