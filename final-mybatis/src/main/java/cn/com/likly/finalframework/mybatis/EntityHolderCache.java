package cn.com.likly.finalframework.mybatis;

import cn.com.likly.finalframework.data.entity.IEntity;
import cn.com.likly.finalframework.data.mapping.Entity;
import cn.com.likly.finalframework.mybatis.mapper.AbsMapper;

import java.io.Serializable;
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
public class EntityHolderCache {
    private final Map<Class<?>, Entity> cache = new LinkedHashMap<>();

    <ID extends Serializable, T extends IEntity<ID>, MAPPER extends AbsMapper<ID, T>> Entity get(Class<MAPPER> mapperClass) {
        if (cache.containsKey(mapperClass)) {
            return cache.get(mapperClass);
        }

        Entity holder = Entity.from(getEntityClass(mapperClass));
        cache.put(mapperClass, holder);
        return holder;
    }

    private <ID extends Serializable, T extends IEntity<ID>, MAPPER extends AbsMapper<ID, T>> Class<T> getEntityClass(Class<MAPPER> mapperClass) {

        for (Type genericInterface : mapperClass.getGenericInterfaces()) {
            if (genericInterface instanceof ParameterizedType && ((ParameterizedType) genericInterface).getRawType().equals(AbsMapper.class)) {
                return (Class<T>) ((ParameterizedType) genericInterface).getActualTypeArguments()[1];
            }
        }

        throw new IllegalArgumentException("");
    }

}
