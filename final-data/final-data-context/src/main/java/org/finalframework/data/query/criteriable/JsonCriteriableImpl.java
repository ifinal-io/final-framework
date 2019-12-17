package org.finalframework.data.query.criteriable;


import org.finalframework.data.query.criterion.Criterion;
import org.finalframework.data.query.criterion.FunctionCriterion;
import org.finalframework.data.query.QProperty;
import org.finalframework.data.query.criterion.function.JsonFunctionCriterion;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-12-16 18:08:26
 * @since 1.0
 */
public class JsonCriteriableImpl<T> extends AbsCriteriable<T, Object> implements JsonCriteriable<T, Object, Criterion> {

    public JsonCriteriableImpl(QProperty<T> property) {
        super(property);
    }

    public JsonCriteriableImpl(QProperty<T> property, FunctionCriterion functions) {
        super(property, functions);
    }

    public JsonCriteriableImpl(QProperty<T> property, Collection<FunctionCriterion> functions) {
        super(property, functions);
    }

    @Override
    public JsonCriteriable<T, Object, Criterion> extract(String path) {
        addFunctionCriterion(JsonFunctionCriterion.extract(path));
        return this;
    }
}

