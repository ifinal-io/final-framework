package org.finalframework.data.query.criterion;

import org.apache.ibatis.type.TypeHandler;
import org.finalframework.core.Assert;
import org.finalframework.data.query.*;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 13:42:22
 * @since 1.0
 */
public abstract class AbsCriterion<T> implements Criterion {

    private static final Set<String> SQL_KEYS = new HashSet<>();

    private final QProperty property;
    private final Collection<FunctionCriterion> functions;
    private final CriterionOperator operator;
    private final Class<? extends TypeHandler> typeHandler;

    @SuppressWarnings("unchecked")
    public AbsCriterion(AbsBuilder builder) {
        this.property = builder.property;
        this.functions = builder.functions;
        this.operator = builder.operator;
        this.typeHandler = builder.typeHandler;
    }

    static {
        SQL_KEYS.add("key");
    }

    @Override
    public QProperty getProperty() {
        return this.property;
    }

    @Override
    public Collection<FunctionCriterion> getFunctions() {
        return this.functions;
    }

    @Override
    public CriterionOperator getOperator() {
        return this.operator;
    }

    @Override
    public Class<? extends TypeHandler> getTypeHandler() {
        return this.typeHandler;
    }

    @Override
    public String getColumn() {
        return getPropertyColumn(property, functions);
    }

    @SuppressWarnings("unchecked")
    protected String getPropertyColumn(QProperty property, Collection<FunctionCriterion> functions) {
        String column = SQL_KEYS.contains(property.getColumn().toLowerCase()) ?
                String.format("`%s`", property.getColumn()) : property.getColumn();

//        final Class<?> javaType = property.isCollectionLike() ? property.getComponentType() : property.getType();
        final Class<?> javaType = property.getType();

        if (Assert.nonEmpty(functions)) {
            for (FunctionCriterion function : functions) {
                column = FunctionOperationRegistry.getInstance().getCriterionOperation(function.operator(), javaType).format(column, function);
            }
        }

        return column;

    }

    @SuppressWarnings("unchecked")
    public static abstract class AbsBuilder<T, R extends Builder> implements Builder<T, R> {
        private QProperty property;
        private Collection<FunctionCriterion> functions = new ArrayList<>();
        private CriterionOperator operator;
        private Class<? extends TypeHandler> typeHandler;

        @Override
        public R property(QProperty property) {
            this.property = property;
            return (R) this;
        }

        @Override
        public R function(FunctionCriterion function) {
            this.functions.add(function);
            return (R) this;
        }

        @Override
        public R function(Collection<FunctionCriterion> functions) {
            this.functions.addAll(functions);
            return (R) this;
        }

        @Override
        public R operator(CriterionOperator operator) {
            this.operator = operator;
            return (R) this;
        }

        @Override
        public R typeHandler(Class<? extends TypeHandler> typeHandler) {
            this.typeHandler = typeHandler;
            return (R) this;
        }
    }
}
