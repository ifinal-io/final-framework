package com.ilikly.finalframework.mybatis.handler;

import com.ilikly.finalframework.data.annotation.enums.PersistentType;
import com.ilikly.finalframework.data.entity.enums.EnumEntity;
import lombok.NonNull;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 13:14
 * @since 1.0
 */
@SuppressWarnings("all")
@Component
public class DefaultTypeHandlerRegistry implements TypeHandlerRegistry, ApplicationContextAware {

    private static final Map<Class<? extends Collection>, Map<Class<?>, TypeHandler<?>>> collectionTypeHandlerMap = new ConcurrentHashMap<>();
    private static final Map<Class<? extends Collection>, Map<Class<?>, TypeHandler<?>>> jsonCollectionTypeHandlerMap = new ConcurrentHashMap<>();
    private static final Map<Class<? extends Collection>, Map<Class<?>, TypeHandler<?>>> jsonCollectionBlobTypeHandlerMap = new ConcurrentHashMap<>();
    private static final Map<Class<?>, TypeHandler<?>> jsonObjectTypeHandlerMap = new ConcurrentHashMap<>();
    private static final Map<Class<? extends EnumEntity>, TypeHandler<? extends EnumEntity>> enumTypeHandlerMap = new ConcurrentHashMap<>();
    private static final Map<Class<?>, TypeHandler<?>> jsonObjectBlobTypeHandlerMap = new ConcurrentHashMap<>();
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    {
        register(short.class, List.class, PersistentType.AUTO, new DefaultListTypeHandler<>(short.class));
        register(Short.class, List.class, PersistentType.AUTO, new DefaultListTypeHandler<>(Short.class));

        register(int.class, List.class, PersistentType.AUTO, new DefaultListTypeHandler<>(int.class));
        register(Integer.class, List.class, PersistentType.AUTO, new DefaultListTypeHandler<>(Integer.class));

        register(long.class, List.class, PersistentType.AUTO, new DefaultListTypeHandler<>(long.class));
        register(Long.class, List.class, PersistentType.AUTO, new DefaultListTypeHandler<>(Long.class));

        register(boolean.class, List.class, PersistentType.AUTO, new DefaultListTypeHandler<>(boolean.class));
        register(Boolean.class, List.class, PersistentType.AUTO, new DefaultListTypeHandler<>(Boolean.class));

        register(String.class, List.class, PersistentType.AUTO, new DefaultListTypeHandler<>(String.class));

        register(short.class, Set.class, PersistentType.AUTO, new DefaultSetTypeHandler(short.class));
        register(Short.class, Set.class, PersistentType.AUTO, new DefaultSetTypeHandler<>(Short.class));

        register(int.class, Set.class, PersistentType.AUTO, new DefaultSetTypeHandler<>(int.class));
        register(Integer.class, Set.class, PersistentType.AUTO, new DefaultSetTypeHandler<>(Integer.class));

        register(long.class, Set.class, PersistentType.AUTO, new DefaultSetTypeHandler<>(long.class));
        register(Long.class, Set.class, PersistentType.AUTO, new DefaultSetTypeHandler<>(Long.class));

        register(boolean.class, Set.class, PersistentType.AUTO, new DefaultSetTypeHandler<>(boolean.class));
        register(Boolean.class, Set.class, PersistentType.AUTO, new DefaultSetTypeHandler<>(Boolean.class));

        register(String.class, Set.class, PersistentType.AUTO, new DefaultSetTypeHandler<>(String.class));

        setDefaultEnumTypeHandler(EnumEntityTypeHandler.class);

    }


    @Override
    public void setDefaultEnumTypeHandler(Class<? extends TypeHandler> typeHandler) {
        if (applicationContext != null) {
            applicationContext.getBeansOfType(SqlSessionFactory.class).values().forEach(it -> it.getConfiguration().getTypeHandlerRegistry().setDefaultEnumTypeHandler(typeHandler));
        }
    }


    @Override
    public void register(Class javaType, Class collectionType, PersistentType jdbcType, TypeHandler typeHandler) {
        switch (jdbcType) {
            case JSON:
                if (collectionType == null) {
                    jsonObjectTypeHandlerMap.put(javaType, typeHandler);
                } else {
                    Map<Class<?>, TypeHandler<?>> typeHandlerMap = jsonCollectionTypeHandlerMap.get(collectionType);
                    if (typeHandlerMap == null) {
                        typeHandlerMap = new ConcurrentHashMap<>();
                        jsonCollectionTypeHandlerMap.put(collectionType, typeHandlerMap);
                    }

                    typeHandlerMap.put(javaType, typeHandler);
                }
                break;
            case BLOB:
                if (collectionType == null) {
                    jsonObjectBlobTypeHandlerMap.put(javaType, typeHandler);
                } else {
                    Map<Class<?>, TypeHandler<?>> typeHandlerMap = jsonCollectionBlobTypeHandlerMap.get(collectionType);
                    if (typeHandlerMap == null) {
                        typeHandlerMap = new ConcurrentHashMap<>();
                        jsonCollectionBlobTypeHandlerMap.put(collectionType, typeHandlerMap);
                    }

                    typeHandlerMap.put(javaType, typeHandler);
                }
                break;
            case AUTO:
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
    public TypeHandler getTypeHandler(Class javaType, Class collectionType, PersistentType jdbcType) {
        switch (jdbcType) {
            case JSON:
                return collectionType == null ? getJsonObjectTypeHandler(javaType) : getJsonCollectionTypeHandler(javaType, collectionType);
            case BLOB:
                return collectionType == null ? getJsonObjectBlobTypeHandler(javaType) : getJsonCollectionBlobTypeHandler(javaType, collectionType);
            case AUTO:
                return collectionType == null ? getTypeHandler(javaType) : getCollectionTypeHandler(javaType, collectionType);
        }

        return getTypeHandler(javaType);
    }

    private TypeHandler getTypeHandler(Class javaType) {

        if (EnumEntity.class.isAssignableFrom(javaType)) {
            return getEnumTypeHandler(javaType);
        }

        if (applicationContext == null) return null;
        List<SqlSessionFactory> collect = new ArrayList<>(applicationContext.getBeansOfType(SqlSessionFactory.class).values());
        return collect.get(0).getConfiguration().getTypeHandlerRegistry().getTypeHandler(javaType);
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

        TypeHandler<?> typeHandler = jsonObjectTypeHandlerMap.get(javaType);

        if (typeHandler == null) {
            typeHandler = new JsonObjectTypeHandler<>(javaType);
            jsonObjectTypeHandlerMap.put(javaType, typeHandler);
        }

        return (TypeHandler<E>) typeHandler;
    }

    private <E, T extends Collection<E>> TypeHandler<T> getJsonCollectionBlobTypeHandler(@NonNull Class<E> javaType, @NonNull Class<T> collectionType) {

        Map<Class<?>, TypeHandler<?>> typeHandlerMap = jsonCollectionBlobTypeHandlerMap.get(collectionType);
        if (typeHandlerMap == null) {
            typeHandlerMap = new ConcurrentHashMap<>();
            jsonCollectionBlobTypeHandlerMap.put(collectionType, typeHandlerMap);
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

    private <E> TypeHandler<E> getJsonObjectBlobTypeHandler(@NonNull Class<E> javaType) {

        TypeHandler<?> typeHandler = jsonObjectBlobTypeHandlerMap.get(javaType);

        if (typeHandler == null) {
            typeHandler = new JsonObjectTypeHandler<>(javaType);
            jsonObjectBlobTypeHandlerMap.put(javaType, typeHandler);
        }

        return (TypeHandler<E>) typeHandler;
    }

    private <E extends EnumEntity> TypeHandler<E> getEnumTypeHandler(Class<E> enumType) {
        if (!enumTypeHandlerMap.containsKey(enumType)) {
            enumTypeHandlerMap.put(enumType, new EnumEntityTypeHandler<>(enumType));
        }
        return (TypeHandler<E>) enumTypeHandlerMap.get(enumType);
    }
}
