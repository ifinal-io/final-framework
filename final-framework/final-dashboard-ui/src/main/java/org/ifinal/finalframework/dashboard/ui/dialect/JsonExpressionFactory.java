package org.ifinal.finalframework.dashboard.ui.dialect;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.ifinal.finalframework.json.JsonRegistry;
import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.expression.IExpressionObjectFactory;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class JsonExpressionFactory implements IExpressionObjectFactory {

    private final String name;

    public JsonExpressionFactory(final String name) {

        this.name = name;
    }

    @Override
    public Set<String> getAllExpressionObjectNames() {
        return new HashSet<>(Collections.singletonList("json"));
    }

    @Override
    public Object buildObject(final IExpressionContext context, final String name) {

        return this.name.equals(name) ? JsonRegistry.getInstance().getJsonService() : null;
    }

    @Override
    public boolean isCacheable(final String name) {

        return this.name.equals(name);
    }

}

