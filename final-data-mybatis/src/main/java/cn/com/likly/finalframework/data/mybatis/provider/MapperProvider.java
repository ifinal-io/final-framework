package cn.com.likly.finalframework.data.mybatis.provider;

import cn.com.likly.finalframework.data.domain.Query;
import cn.com.likly.finalframework.data.domain.Update;
import cn.com.likly.finalframework.data.entity.IEntity;
import cn.com.likly.finalframework.data.mapping.Entity;
import cn.com.likly.finalframework.data.mybatis.handler.TypeHandlerRegistry;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-17 09:41
 * @since 1.0
 */

@Component
@SuppressWarnings({"unchecked", "unused"})
public class MapperProvider<ID extends Serializable, T extends IEntity> implements ApplicationContextAware {

    private static TypeHandlerRegistry typeHandlerRegistry;


    public String insert(Map<String, Object> args) {
        Entity<T> holder = (Entity<T>) args.get("holder");
        Collection<T> list = args.containsKey("array") ? Arrays.asList((T[]) args.get("array")) : (Collection<T>) args.get(
                "list");

        return new DefaultInsertProvider<T>(typeHandlerRegistry).INSERT_INTO(holder).INSERT_VALUES(list).provide();
    }

    public String update(Map<String, Object> args) {
        Entity<T> holder = (Entity<T>) args.get("holder");
        T entity = args.containsKey("entity") ? (T) args.get("entity") : holder.getInstance();
        if (entity == null) {
            entity = holder.getInstance();
        }
        Update update = (Update) args.get("update");
        Query query = (Query) args.get("query");

        return new DefaultUpdateProvider<T>(typeHandlerRegistry)
                .UPDATE(holder)
                .ENTITY(entity)
                .SET(update)
                .QUERY(query)
                .provide();


    }

    public String delete(Map<String, Object> args) {
        Entity<T> holder = (Entity<T>) args.get("holder");
        Query query = (Query) args.get("query");
        return new DefaultDeleteProvider<T>(typeHandlerRegistry).DELETE(holder).QUERY(query).provide();
    }

    public String select(Map<String, Object> args) {
        Entity<T> holder = (Entity<T>) args.get("holder");
        Query query = (Query) args.get("query");
        return new DefaultSelectProvider<T>(typeHandlerRegistry).SELECT(holder).QUERY(query).provide();
    }

    public String selectCount(Map<String, Object> args) {
        Entity<T> holder = (Entity<T>) args.get("holder");
        Query query = (Query) args.get("query");
        return new DefaultSelectProvider<T>(typeHandlerRegistry).SELECT_COUNT(holder).QUERY(query).provide();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        typeHandlerRegistry = applicationContext.getBean(TypeHandlerRegistry.class);
    }


}
