package com.ilikly.finalframework.data.query.criterion.operation;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 13:42:22
 * @since 1.0
 */
public abstract class AbsCollectionCriterionOperation<E> extends AbsCriterionOperation<Collection<E>> {

    protected String buildInString(Collection<E> collection) {
        return collection.stream()
                .map(item -> item instanceof String ? String.format("'%s'", item) : item.toString())
                .collect(Collectors.joining(",", "(", ")"));

    }

}
