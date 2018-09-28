package cn.com.likly.finalframework.mybatis.handler;

import cn.com.likly.finalframework.mybatis.annotation.enums.JdbcType;
import lombok.NonNull;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 13:14
 * @since 1.0
 */
@SuppressWarnings("unchecked")
@Component
public class DefaultTypeHandlerRegistry implements TypeHandlerRegistry {

    private static final Map<Class<? extends Collection>, Map<Class<?>, TypeHandler<?>>> collectionTypeHandlerMap = new ConcurrentHashMap<>();
    private static final Map<Class<? extends Collection>, Map<Class<?>, TypeHandler<?>>> jsonCollectionTypeHandlerMap = new ConcurrentHashMap<>();
    private static final Map<Class<?>, TypeHandler<?>> jsonTypeHandlerMap = new ConcurrentHashMap<>();


    @Resource
    private SqlSessionFactory sqlSessionFactory;

    @PostConstruct
    private void init() {

        register(short.class, List.class, JdbcType.DEFAULT, new DefaultListTypeHandler<>(short.class));
        register(Short.class, List.class, JdbcType.DEFAULT, new DefaultListTypeHandler<>(Short.class));

        register(int.class, List.class, JdbcType.DEFAULT, new DefaultListTypeHandler<>(int.class));
        register(Integer.class, List.class, JdbcType.DEFAULT, new DefaultListTypeHandler<>(Integer.class));

        register(long.class, List.class, JdbcType.DEFAULT, new DefaultListTypeHandler<>(long.class));
        register(Long.class, List.class, JdbcType.DEFAULT, new DefaultListTypeHandler<>(Long.class));

        register(boolean.class, List.class, JdbcType.DEFAULT, new DefaultListTypeHandler<>(boolean.class));
        register(Boolean.class, List.class, JdbcType.DEFAULT, new DefaultListTypeHandler<>(Boolean.class));

        register(String.class, List.class, JdbcType.DEFAULT, new DefaultListTypeHandler<>(String.class));

        register(short.class, Set.class, JdbcType.DEFAULT, new DefaultSetTypeHandler(short.class));
        register(Short.class, Set.class, JdbcType.DEFAULT, new DefaultSetTypeHandler<>(Short.class));

        register(int.class, Set.class, JdbcType.DEFAULT, new DefaultSetTypeHandler<>(int.class));
        register(Integer.class, Set.class, JdbcType.DEFAULT, new DefaultSetTypeHandler<>(Integer.class));

        register(long.class, Set.class, JdbcType.DEFAULT, new DefaultSetTypeHandler<>(long.class));
        register(Long.class, Set.class, JdbcType.DEFAULT, new DefaultSetTypeHandler<>(Long.class));

        register(boolean.class, Set.class, JdbcType.DEFAULT, new DefaultSetTypeHandler<>(boolean.class));
        register(Boolean.class, Set.class, JdbcType.DEFAULT, new DefaultSetTypeHandler<>(Boolean.class));

        register(String.class, Set.class, JdbcType.DEFAULT, new DefaultSetTypeHandler<>(String.class));

        setDefaultEnumTypeHandler(EnumEntityTypeHandler.class);

    }


    @Override
    public void setDefaultEnumTypeHandler(Class<? extends TypeHandler> typeHandler) {
        sqlSessionFactory.getConfiguration().getTypeHandlerRegistry().setDefaultEnumTypeHandler(typeHandler);
    }


    @Override
    public void register(Class javaType, Class collectionType, JdbcType jdbcType, TypeHandler typeHandler) {
        switch (jdbcType) {
            case JSON:
                if (collectionType == null) {
                    jsonTypeHandlerMap.put(javaType, typeHandler);
                } else {
                    Map<Class<?>, TypeHandler<?>> typeHandlerMap = jsonCollectionTypeHandlerMap.get(collectionType);
                    if (typeHandlerMap == null) {
                        typeHandlerMap = new ConcurrentHashMap<>();
                        jsonCollectionTypeHandlerMap.put(collectionType, typeHandlerMap);
                    }

                    typeHandlerMap.put(javaType, typeHandler);
                }
                break;
            case DEFAULT:
                if (collectionType != null) {
                    Map<Class<?>, TypeHandler<?>> typeHandlerMap = collectionTypeHandlerMap.get(collectionType);
                    if (typeHandlerMap == null) {
                        typeHandlerMap = new ConcurrentHashMap<>();
                        collectionTypeHandlerMap.put(collectionType, typeHandlerMap);
                    }
                    typeHandlerMap.put(javaType, typeHandler);
                }
        }
    }

    @Override
    public TypeHandler getTypeHandler(Class javaType, Class collectionType, JdbcType jdbcType) {

        switch (jdbcType) {
            case JSON:
                return collectionType == null ? getJsonObjectTypeHandler(javaType) : getJsonCollectionTypeHandler(javaType, collectionType);
            case DEFAULT:
                return collectionType == null ? getTypeHandler(javaType) : getCollectionTypeHandler(javaType, collectionType);
        }

        return getTypeHandler(javaType);
    }

    private TypeHandler getTypeHandler(Class javaType) {
        return sqlSessionFactory.getConfiguration().getTypeHandlerRegistry().getTypeHandler(javaType);
    }

    private <E, T extends Collection<E>> TypeHandler<T> getCollectionTypeHandler(@NonNull Class<E> javaType, @NonNull Class<T> collectionType) {

        Map<Class<?>, TypeHandler<?>> typeHandlerMap = collectionTypeHandlerMap.get(collectionType);
        if (typeHandlerMap == null) {
            return null;
        }
        TypeHandler<?> typeHandler = typeHandlerMap.get(javaType);
        return typeHandler == null ? null : (TypeHandler<T>) typeHandler;
    }

    private <E, T extends Collection<E>> TypeHandler<T> getJsonCollectionTypeHandler(@NonNull Class<E> javaType, @NonNull Class<T> collectionType) {

        Map<Class<?>, TypeHandler<?>> typeHandlerMap = jsonCollectionTypeHandlerMap.get(collectionType);
        if (typeHandlerMap == null) {
            typeHandlerMap = new ConcurrentHashMap<>();
            jsonCollectionTypeHandlerMap.put(collectionType, typeHandlerMap);
        }

        TypeHandler<?> typeHandler = typeHandlerMap.get(javaType);

        if (typeHandler == null) {
            if (List.class.isAssignableFrom(collectionType)) {
                typeHandler = new JsonListTypeHandler<>(javaType);
            }

            if (Set.class.isAssignableFrom(collectionType)) {
                typeHandler = new JsonSetTypeHandler<>(javaType);
            }

            if (typeHandler != null) {
                typeHandlerMap.put(javaType, typeHandler);
            }

        }

        return (TypeHandler<T>) typeHandler;
    }

    private <E> TypeHandler<E> getJsonObjectTypeHandler(@NonNull Class<E> javaType) {

        TypeHandler<?> typeHandler = jsonTypeHandlerMap.get(javaType);

        if (typeHandler == null) {
            typeHandler = new JsonObjectTypeHandler<>(javaType);
            jsonTypeHandlerMap.put(javaType, typeHandler);
        }

        return (TypeHandler<E>) typeHandler;
    }
}
