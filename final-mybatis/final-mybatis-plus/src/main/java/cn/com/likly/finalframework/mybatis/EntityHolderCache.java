package cn.com.likly.finalframework.mybatis;

import cn.com.likly.finalframework.context.entity.Entity;
import cn.com.likly.finalframework.mybatis.factory.EntityHolderFactory;
import cn.com.likly.finalframework.mybatis.holder.EntityHolder;
import cn.com.likly.finalframework.mybatis.mapper.AbsMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 21:14
 * @since 1.0
 */
@SuppressWarnings("unchecked")
@Component
public class EntityHolderCache {
    private final Map<Class<?>, EntityHolder> cache = new LinkedHashMap<>();

    @Resource
    private EntityHolderFactory entityHolderFactory;


    <ID, T extends Entity<ID>, MAPPER extends AbsMapper<ID, T>> EntityHolder get(Class<MAPPER> mapperClass) {
        if (cache.containsKey(mapperClass)) {
            return cache.get(mapperClass);
        }

        EntityHolder holder = entityHolderFactory.create(getEntityClass(mapperClass));
        cache.put(mapperClass, holder);
        return holder;
    }

    private <ID, T extends Entity<ID>, MAPPER extends AbsMapper<ID, T>> Class<T> getEntityClass(Class<MAPPER> mapperClass) {

        for (Type genericInterface : mapperClass.getGenericInterfaces()) {
            if (genericInterface instanceof ParameterizedType && ((ParameterizedType) genericInterface).getRawType().equals(AbsMapper.class)) {
                return (Class<T>) ((ParameterizedType) genericInterface).getActualTypeArguments()[1];
            }
        }

        throw new IllegalArgumentException("");
    }

}
