package org.finalframework.data.query.converter;

import org.finalframework.core.converter.Converter;
import org.finalframework.data.query.IQuery;
import org.finalframework.data.query.Query;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-19 23:12:15
 * @since 1.0
 */
public interface QueryConverter<T extends IQuery> extends Converter<T, Query> {

}
