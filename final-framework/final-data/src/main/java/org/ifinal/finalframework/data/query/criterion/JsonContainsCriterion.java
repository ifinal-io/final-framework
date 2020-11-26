package org.ifinal.finalframework.data.query.criterion;


import org.ifinal.finalframework.data.query.SqlNode;
import org.ifinal.finalframework.data.query.operation.JsonOperation;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface JsonContainsCriterion extends SingleCriterion<Object>, SqlNode {

    static JsonContainsCriterion contains(Object target, Object value, String path) {
        return new JsonContainsCriterionImpl(target, JsonOperation.JSON_CONTAINS, value, path);
    }

    static JsonContainsCriterion notContains(Object target, Object value, String path) {
        return new JsonContainsCriterionImpl(target, JsonOperation.NOT_JSON_CONTAINS, value, path);
    }

    String getPath();


}
