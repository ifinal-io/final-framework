package org.ifinal.finalframework.dashboard.ui.dialect;

import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class JsonDialect extends AbstractDialect implements IExpressionObjectDialect {

    private static final String EXPRESSION_NAME = "json";

    public JsonDialect() {
        super(EXPRESSION_NAME);
    }

    @Override
    public IExpressionObjectFactory getExpressionObjectFactory() {
        return new JsonExpressionFactory(EXPRESSION_NAME);
    }

}

