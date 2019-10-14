package org.finalframework.data.query;

import org.finalframework.core.converter.Convertible;

/**
 * 可查询的对象,实现将自定义的查询对象转化为{@link QueryImpl}对象
 *
 * @author likly
 * @version 1.0
 * @date 2019-05-08 14:00:52
 * @since 1.0
 */
public interface Queryable extends Convertible<QueryImpl> {

}
