

package org.finalframework.spring.expression;


import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * @author likly
 * @version 1.0
 * @date 2020-07-17 09:56:29
 * @since 1.0
 */
public class Spel {

    private final SpelExpressionParser parser;

    public Spel() {
        this(new SpelExpressionParser());
    }

    public Spel(SpelExpressionParser parser) {
        this.parser = parser;
    }

    public Expression expression(String expression) {
        return this.parser.parseExpression(expression);
    }

}

