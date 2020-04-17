package org.finalframework.mybatis.resumtmap;


import org.apache.ibatis.mapping.ResultFlag;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;
import org.finalframework.data.annotation.IEnum;
import org.finalframework.data.annotation.Reference;
import org.finalframework.data.annotation.enums.ReferenceMode;
import org.finalframework.data.mapping.Entity;
import org.finalframework.data.mapping.Property;
import org.finalframework.data.mapping.converter.NameConverterRegistry;
import org.finalframework.mybatis.handler.EnumTypeHandler;
import org.finalframework.mybatis.handler.JsonTypeReferenceTypeHandler;
import org.springframework.lang.NonNull;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2020-04-12 15:44:14
 * @since 1.0
 */
public class ResultMapFactory {

    private static final Map<Class<?>, ResultMap> resultMaps = new ConcurrentHashMap<>();

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
                                    final String column = formatColumn(property, referenceProperty);
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
                                .column(formatColumn(null, property))
                                .javaType(type)
                                .flags(property.isIdProperty() ? Collections.singletonList(ResultFlag.ID) : Collections.emptyList())
                                .composites(composites)
//                                .nestedResultMapId(associationId)
                                // a composting result mapping is not need a typehandler, but mybatis have this a validate.
                                .typeHandler(configuration.getTypeHandlerRegistry().getUnknownTypeHandler())
                                .build();


                    } else {
                        final String name = property.getName();
                        final String column = formatColumn(null, property);
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

    private static String formatColumn(Property property, Property referenceProperty) {
        String column = null;
        if (property == null) {
            column = referenceProperty.getColumn();
//            if (property.isKeyword()) {
//                column = String.format("`%s`", column);
//            }

        } else {
            final String referenceColumn = property.getReferenceColumn(referenceProperty);
            column = referenceProperty.isIdProperty() && property.getReferenceMode() == ReferenceMode.SIMPLE ?
                    property.getColumn() : property.getColumn() + referenceColumn.substring(0, 1).toUpperCase() + referenceColumn.substring(1);
        }

        return NameConverterRegistry.getInstance().getColumnNameConverter().convert(column);
    }


    private static TypeHandler<?> findTypeHandler(Configuration configuration, Property property) {
        if (property.isCollectionLike() || property.isMap()) {
            return new JsonTypeReferenceTypeHandler<>(property.getField().getGenericType());
        }
        if (property.isEnum()) {
            final Class<? extends IEnum> type = (Class<? extends IEnum>) property.getType();
            return new EnumTypeHandler<>(type);
        }
        return configuration.getTypeHandlerRegistry().getTypeHandler(property.getType());
    }


}

