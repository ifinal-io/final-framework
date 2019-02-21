package com.ilikly.finalframework.data.query.converter;

import com.ilikly.finalframework.core.converter.Converter;
import com.ilikly.finalframework.data.query.IQuery;
import com.ilikly.finalframework.data.query.Query;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-19 23:12:15
 * @since 1.0
 */
public interface QueryConverter<T extends IQuery> extends Converter<T, Query> {
}
