package cn.com.likly.finalframework.mybatis.factory;

import cn.com.likly.finalframework.context.entity.Entity;
import cn.com.likly.finalframework.mybatis.holder.TableHolder;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-26 22:14
 * @since 1.0
 */
public interface TableHolderFactory {

    <T extends Entity> TableHolder parse(Class<T> entityClass);

}
