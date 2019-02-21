package com.ilikly.finalframework.data.query.converter;

import com.ilikly.finalframework.data.query.IQuery;
import com.ilikly.finalframework.data.query.Query;
import lombok.NonNull;

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
