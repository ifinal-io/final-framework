package cn.com.likly.finalframework.spring.expression;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-28 09:51
 * @since 1.0
 */
public class SpelParser {
    private final Object root;
    private final ExpressionParser parser;

    public SpelParser(Object root, ExpressionParser parser) {
        this.root = root;
        this.parser = parser;
    }

    public SpelParser(Object root) {
        this(root, new SpelExpressionParser());
    }

    public <T> T getValue(String expression, Class<T> clazz, T defVal) {
        Expression exp = parser.parseExpression(expression);
        final Object value =
                clazz == null ? root == null ? exp.getValue() : exp.getValue(new StandardEvaluationContext(root))
                        : root == null ? exp.getValue(clazz) : exp.getValue(new StandardEvaluationContext(root), clazz);
        return value == null ? defVal : (T) value;
    }

    public <T> T getValue(String expression) {
        return getValue(expression, null, null);
    }

    public Boolean getBoolean(String expression, Boolean defVal) {
        return getValue(expression, Boolean.class, defVal);
    }

    public String getString(String expression, String defVal) {
        return getValue(expression, String.class, defVal);
    }

    public Integer getInt(String expression, Integer defVal) {
        return getValue(expression, Integer.class, defVal);
    }

    public Long getLong(String expression, Long defVal) {
        return getValue(expression, Long.class, defVal);
    }
}
