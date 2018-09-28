package cn.com.likly.finalframework.mybatis.factory;

import cn.com.likly.finalframework.context.entity.Entity;
import cn.com.likly.finalframework.mybatis.holder.EntityHolder;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-26 22:12
 * @since 1.0
 */
public interface EntityHolderFactory {

    <ID, T extends Entity<ID>> EntityHolder create(Class<T> entityClass);

}
