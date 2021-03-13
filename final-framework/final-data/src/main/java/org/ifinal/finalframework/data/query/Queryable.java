package org.ifinal.finalframework.data.query;

import org.ifinal.finalframework.util.function.Convertible;

/**
 * 可查询的对象,实现将自定义的查询对象转化为{@link Query}对象
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Queryable extends Convertible<Query> {

}
