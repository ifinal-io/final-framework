package cn.com.likly.finalframework.mybatis.provider;

import cn.com.likly.finalframework.data.domain.Query;
import cn.com.likly.finalframework.data.domain.Update;
import cn.com.likly.finalframework.data.mapping.Entity;
import cn.com.likly.finalframework.data.provider.UpdateProvider;
import cn.com.likly.finalframework.mybatis.handler.TypeHandlerRegistry;

import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-20 00:15
 * @since 1.0
 */
public class DefaultUpdateProvider<T> extends AbsProvider<T> implements UpdateProvider<T> {

    private Entity<T> holder;
    private T entity;
    private Update update;
    private Query query;


    public DefaultUpdateProvider(TypeHandlerRegistry typeHandlerRegistry) {
        super(typeHandlerRegistry);
    }

    @Override
    public UpdateProvider<T> UPDATE(Entity<T> holder) {
        this.holder = holder;
        return this;
    }

    @Override
    public UpdateProvider<T> SET(Update update) {
        this.update = update;
        return this;
    }

    @Override
    public UpdateProvider<T> ENTITY(T entity) {
        this.entity = entity;
        return this;
    }

    @Override
    public UpdateProvider<T> QUERY(Query query) {
        this.query = query;
        return this;
    }


    private String getUpdateSet() {
        if (update == null) {
            return holder.stream()
                         .filter(it -> !it.isTransient() && it.updatable() && (it.nonnull() && it.get(entity) != null))
                         // ${column} = #{entity.${property}}
                         .map(it -> String.format("%s = #{entity.%s %s}",
                            it.getColumn(),
                            it.getName(),
                            getJavaTypeAndTypeHandler(it))
                    )
                         .collect(Collectors.joining(","));
        } else {
            return update.stream()
                    .filter(it -> !it.getProperty().isTransient()
                            && it.getProperty().updatable() && (it.getProperty().nonnull() && !it.isNull()))
                    .map(it -> {
                        it.getProperty().set(entity, it.getValue());

                        switch (it.getOperation()) {
                            case EQUAL:
                                return String.format("%s = #{entity.%s %s}",
                                        it.getProperty().getColumn(),
                                        it.getProperty().getName(),
                                        getJavaTypeAndTypeHandler(it.getProperty()));
                            case INC:
                                return String.format("%s = %s + 1",
                                        it.getProperty().getColumn(),
                                        it.getProperty().getColumn());
                            case INCR:
                                return String.format("%s = %s + #{entity.%s %s}",
                                        it.getProperty().getColumn(),
                                        it.getProperty().getColumn(),
                                        it.getProperty().getName(),
                                        getJavaTypeAndTypeHandler(it.getProperty()));
                            case DEC:
                                return String.format("%s = %s - 1", it.getProperty().getColumn(), it.getProperty().getColumn());
                            case DECR:
                                return String.format("%s = %s - #{entity.%s %s}",
                                        it.getProperty().getColumn(),
                                        it.getProperty().getColumn(),
                                        it.getProperty().getName(),
                                        getJavaTypeAndTypeHandler(it.getProperty()));
                            default:
                                return "";
                        }


                    })
                    .collect(Collectors.joining(","));
        }
    }

    @Override
    public String provide() {

        final StringBuilder sb = new StringBuilder();

        sb.append("SET ")
                .append(getTable(holder))
                .append(" SET ")
                .append(getUpdateSet())
                .append(getQuery(query));

        return sb.toString();
    }
}
