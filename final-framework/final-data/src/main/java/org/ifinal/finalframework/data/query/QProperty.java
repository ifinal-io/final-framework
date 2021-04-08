package org.ifinal.finalframework.data.query;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import org.ifinal.finalframework.data.mapping.Property;
import org.ifinal.finalframework.query.Criteriable;
import org.ifinal.finalframework.query.Criterion;
import org.ifinal.finalframework.query.CriterionAttributes;
import org.ifinal.finalframework.query.CriterionTarget;
import org.ifinal.finalframework.query.FunctionCriteriable;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

import org.apache.ibatis.type.TypeHandler;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface QProperty<T> extends Comparable<QProperty<T>>, FunctionCriteriable<Object>, Sortable<Order>{

    static <T, E extends QEntity<?, ?>> QProperty.Builder<T> builder(E entity, Property property) {
        return new QPropertyImpl.BuilderImpl<>(entity, property);
    }

    <E extends QEntity<?, ?>> E getEntity();

    Property getProperty();

    @SuppressWarnings("unchecked")
    default Class<T> getType() {
        return (Class<T>) getProperty().getJavaType();
    }

    Integer getOrder();

    String getPath();

    String getTable();

    String getName();

    @Nullable
    default String getWriter() {
        return this.getProperty().getWriter();
    }

    @Nullable
    default String getReader() {
        return this.getProperty().getReader();
    }

    String getColumn();

    boolean isIdProperty();

    default boolean isVersionProperty() {
        return getProperty().isVersionProperty();
    }

    boolean isReadable();

    /**
     * @return isWriteable
     * @see Property#isWriteable()
     */
    boolean isWriteable();

    /**
     * @return isModifiable
     * @see Property#isModifiable()
     */
    boolean isModifiable();

    Class<? extends TypeHandler> getTypeHandler();

    /**
     * Returns whether the property is an array.
     */
    boolean isArray();

    boolean hasView(@Nullable Class<?> view);

    boolean unique();

    boolean nonnull();

    @Override
    default Criterion condition(@NonNull String expression, @Nullable Object value, @Nullable Consumer<CriterionAttributes> consumer) {
       return CriterionTarget.from(getColumn()).condition(expression,value,consumer);
    }

    @Override
    default Criteriable<Object> apply(@NonNull UnaryOperator<String> column, @Nullable Consumer<CriterionAttributes> consumer){
        return CriterionTarget.from(getColumn()).apply(column,consumer);
    }

    @Override
    default Order asc() {
        return Order.asc(this);
    }

    @Override
    default Order desc() {
        return Order.desc(this);
    }

    @Override
    default int compareTo(QProperty<T> o) {
        return Integer.compare(this.getOrder(), o.getOrder());
    }

    /**
     * Builder.
     *
     * @param <T> type.
     */
    interface Builder<T> extends org.ifinal.finalframework.util.Builder<QProperty<T>> {

        Builder<T> order(Integer order);

        Builder<T> path(String path);

        Builder<T> name(String name);

        Builder<T> column(String column);

        Builder<T> idProperty(boolean idProperty);

        Builder<T> readable(boolean readable);

        Builder<T> writeable(boolean writeable);

        Builder<T> modifiable(boolean modifiable);

        Builder<T> typeHandler(Class<? extends TypeHandler> typeHandler);

        Builder<T> views(List<Class<?>> views);

    }

}
