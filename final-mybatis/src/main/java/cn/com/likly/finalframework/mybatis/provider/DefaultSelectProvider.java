package cn.com.likly.finalframework.mybatis.provider;

import cn.com.likly.finalframework.data.domain.Query;
import cn.com.likly.finalframework.data.mapping.holder.EntityHolder;
import cn.com.likly.finalframework.data.mapping.holder.PropertyHolder;
import cn.com.likly.finalframework.data.provider.SelectProvider;
import cn.com.likly.finalframework.mybatis.handler.TypeHandlerRegistry;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-19 11:12
 * @since 1.0
 */
@Slf4j
@Service
public class DefaultSelectProvider<T> extends AbsProvider<T> implements SelectProvider<T> {

    private SelectType selectType;
    private EntityHolder<T> holder;
    private Query query;

    public DefaultSelectProvider(@NonNull TypeHandlerRegistry typeHandlerRegistry) {
        super(typeHandlerRegistry);
    }

    @Override
    protected PropertyHolder getPropertyHolder(String property) {
        return holder.getRequiredPersistentProperty(property);
    }

    @Override
    public SelectProvider<T> SELECT(@NonNull EntityHolder<T> holder) {
        this.holder = holder;
        this.selectType = SelectType.SELECT;
        return this;
    }

    @Override
    public SelectProvider<T> SELECT_COUNT(EntityHolder<T> holder) {
        this.holder = holder;
        this.selectType = SelectType.SELECT_COUNT;
        return this;
    }

    @Override
    public SelectProvider<T> QUERY(Query query) {
        this.query = query;
        return this;
    }

    private String getSelectColumns(EntityHolder<T> entity) {
        return entity.stream().filter(it -> !it.isTransient())
                .map(this::getSelectColumn)
                .collect(Collectors.joining(","));
    }

    private String getSelectColumn(PropertyHolder property) {
        if (property.getName().equals(property.getColumn())) {
            return property.getName();
        } else {
            return String.format("%s AS %s", property.getColumn(), property.getName());
        }
    }

    @Override
    public String provide() {

        final StringBuilder sb = new StringBuilder();

        switch (selectType) {
            case SELECT:
                sb.append("SELECT ").append(getSelectColumns(holder));
                break;
            case SELECT_COUNT:
                sb.append("SELECT COUNT(*)");
                break;
        }


        sb.append(" FROM ")
                .append(getTable(holder))
                .append(getQuery(query));


        String sql = sb.toString();
        logger.info("==> {}", sql);
        return sql;
    }


    private enum SelectType {
        SELECT,
        SELECT_COUNT,
    }
}
