package cn.com.likly.finalframework.data.mybatis.provider;

import cn.com.likly.finalframework.data.domain.Query;
import cn.com.likly.finalframework.data.mapping.Entity;
import cn.com.likly.finalframework.data.provider.DeleteProvider;
import cn.com.likly.finalframework.data.mybatis.handler.TypeHandlerRegistry;
import lombok.extern.slf4j.Slf4j;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-19 22:49
 * @since 1.0
 */
@Slf4j
public class DefaultDeleteProvider<T> extends AbsProvider<T> implements DeleteProvider<T> {

    private Entity<T> entity;
    private Query query;


    public DefaultDeleteProvider(TypeHandlerRegistry typeHandlerRegistry) {
        super(typeHandlerRegistry);
    }

    @Override
    public DeleteProvider<T> DELETE(Entity<T> entity) {
        this.entity = entity;
        return this;
    }

    @Override
    public DeleteProvider<T> QUERY(Query query) {
        this.query = query;
        return this;
    }

    @Override
    public String provide() {
        return "DELETE FROM " + getTable(entity) + getQuery(query);
    }
}
