package org.ifinal.finalframework.mybatis.resumtmap;


import org.apache.ibatis.mapping.ResultFlag;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;
import org.ifinal.finalframework.annotation.IEnum;
import org.ifinal.finalframework.annotation.data.Json;
import org.ifinal.finalframework.annotation.data.Reference;
import org.ifinal.finalframework.annotation.data.ReferenceMode;
import org.ifinal.finalframework.annotation.data.UpperCase;
import org.ifinal.finalframework.data.mapping.Entity;
import org.ifinal.finalframework.data.mapping.Property;
import org.ifinal.finalframework.data.mapping.converter.NameConverterRegistry;
import org.ifinal.finalframework.data.query.type.JsonParameterTypeHandler;
import org.ifinal.finalframework.mybatis.handler.EnumTypeHandler;
import org.ifinal.finalframework.mybatis.handler.JsonTypeReferenceTypeHandler;
import org.springframework.lang.NonNull;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public final class ResultMapFactory {

    private static final Map<Class<?>, ResultMap> resultMaps = new ConcurrentHashMap<>();

    private ResultMapFactory() {
    }

    public static ResultMap from(@NonNull Configuration configuration, Class<?> entity) {
        return from(configuration, Entity.from(entity));
    }

    public static ResultMap from(@NonNull Configuration configuration, Entity<?> entity) {
        return resultMaps.computeIfAbsent(entity.getType(), key -> createResultMap(configuration, entity));
    }


    private static ResultMap createResultMap(Configuration configuration, Entity<?> entity) {

        final String id = entity.getType().getCanonicalName();

        final List<ResultMapping> resultMappings = entity.stream()
                .filter(it -> !it.isTransient() && !it.isVirtual() && !it.isWriteOnly())
                .map(property -> {
                    final Class<?> type = property.getType();
                    if (property.isAssociation()) {

                        final Reference reference = property.getRequiredAnnotation(Reference.class);
                        final Entity<?> referenceEntity = Entity.from(type);
                        final List<ResultMapping> composites = Arrays.stream(reference.properties())
                                .map(referenceEntity::getPersistentProperty)
                                .map(referenceProperty -> {

                                    final String name = referenceProperty.getName();
                                    String column = formatColumn(entity, property, referenceProperty);
                                    final TypeHandler<?> typeHandler = findTypeHandler(configuration, referenceProperty);

                                    return new ResultMapping.Builder(configuration, name, column, type)
                                            .javaType(type)
                                            .flags(referenceProperty.isIdProperty() ? Collections.singletonList(ResultFlag.ID) : Collections.emptyList())
                                            .typeHandler(typeHandler)
                                            .build();
                                })
                                .collect(Collectors.toList());


                        final String name = property.getName();


                        return new ResultMapping.Builder(configuration, name)
                                .column(formatColumn(entity, null, property))
                                .javaType(type)
                                .flags(property.isIdProperty() ? Collections.singletonList(ResultFlag.ID) : Collections.emptyList())
                                .composites(composites)
                                .nestedResultMapId(id + "[" + name + "]")
                                // a composting result mapping is not need a typehandler, but mybatis have this a validate.
                                .typeHandler(configuration.getTypeHandlerRegistry().getUnknownTypeHandler())
                                .build();


                    } else {
                        final String name = property.getName();
                        final String column = formatColumn(entity, null, property);

                        final TypeHandler<?> typeHandler = findTypeHandler(configuration, property);

                        return new ResultMapping.Builder(configuration, name, column, type)
                                .flags(property.isIdProperty() ? Collections.singletonList(ResultFlag.ID) : Collections.emptyList())
                                .typeHandler(typeHandler)
                                .build();
                    }

                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());


        return new ResultMap.Builder(configuration, id, entity.getType(), resultMappings)
                .build();


    }

    private static String formatColumn(Entity<?> entity, Property property, Property referenceProperty) {
        String column;
        if (property == null) {
            column = referenceProperty.getColumn();
        } else {
            final String referenceColumn = property.getReferenceColumn(referenceProperty);
            column = referenceProperty.isIdProperty() && property.getReferenceMode() == ReferenceMode.SIMPLE ?
                    property.getColumn() : property.getColumn() + referenceColumn.substring(0, 1).toUpperCase() + referenceColumn.substring(1);
        }
        column = NameConverterRegistry.getInstance().getColumnNameConverter().convert(column);
        if (Optional.ofNullable(property).orElse(referenceProperty).isAnnotationPresent(UpperCase.class) || entity.isAnnotationPresent(UpperCase.class)) {
            column = column.toUpperCase();
        }
        return column;
    }


    private static TypeHandler<?> findTypeHandler(Configuration configuration, Property property) {

        Field field = property.getField();
        try {
            Class<? extends TypeHandler> typeHandler = property.getTypeHandler();
            if (JsonParameterTypeHandler.class.equals(typeHandler)) {
                Objects.requireNonNull(field);
                return new JsonTypeReferenceTypeHandler<>(field.getGenericType());
            }


            if (typeHandler != null && !TypeHandler.class.equals(typeHandler)) {
                return typeHandler.getConstructor().newInstance();
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }


        if (property.isAnnotationPresent(Json.class) || property.isCollectionLike() || property.isMap()) {
            Objects.requireNonNull(field);
            return new JsonTypeReferenceTypeHandler<>(field.getGenericType());
        }
        if (property.isEnum()) {
            final Class<? extends IEnum<?>> type = (Class<? extends IEnum<?>>) property.getType();
            return new EnumTypeHandler<>(type);
        }
        return configuration.getTypeHandlerRegistry().getTypeHandler(property.getType());
    }


}

