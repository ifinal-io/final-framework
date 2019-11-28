package org.finalframework.data.query.converter;

import lombok.NonNull;
import org.finalframework.data.query.IQuery;
import org.finalframework.data.query.Query;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-19 23:13:14
 * @since 1.0
 */
public class DefaultQueryConverter<T extends IQuery> implements QueryConverter<T> {
    @Override
    public Query convert(@NonNull T query) {

        final Query result = new Query();

        return result;
    }
}