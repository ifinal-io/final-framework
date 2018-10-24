package cn.com.likly.finalframework.data.provider;

import cn.com.likly.finalframework.data.domain.Query;
import cn.com.likly.finalframework.data.mapping.holder.EntityHolder;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-18 22:06
 * @since 1.0
 */
public interface DeleteProvider<T> extends Provider<String> {

    DeleteProvider<T> DELETE(EntityHolder<T> entity);

    DeleteProvider<T> QUERY(Query query);

}
