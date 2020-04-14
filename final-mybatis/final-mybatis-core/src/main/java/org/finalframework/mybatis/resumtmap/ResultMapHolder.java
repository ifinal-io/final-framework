package org.finalframework.mybatis.resumtmap;


import org.apache.ibatis.mapping.ResultFlag;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;
import org.finalframework.data.annotation.Reference;
import org.finalframework.data.annotation.enums.ReferenceMode;
import org.finalframework.data.entity.AbsEntity;
import org.finalframework.data.mapping.Entity;
import org.finalframework.data.mapping.Property;
import org.finalframework.mybatis.handler.TypeReferenceJsonTypeHandler;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2020-04-12 15:44:14
 * @since 1.0
 */
public class ResultMapHolder {
    private final Configuration configuration;
    private final Entity<?> entity;
    private final ResultMap resultMap;

    public ResultMapHolder(Configuration configuration, Entity<?> entity) {
        this.configuration = configuration;
        this.entity = entity;

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

                                    final ReferenceMode mode = reference.mode();

                                    final String name = referenceProperty.getName();
                                    final String column = ReferenceMode.SIMPLE == mode && referenceProperty.isIdProperty() ? property.getColumn() : property.getColumn() + referenceProperty.getColumn();
                                    final TypeHandler<?> typeHandler = findTypeHandler(referenceProperty);

                                    return new ResultMapping.Builder(configuration, name, column, type)
                                            .javaType(type)
                                            .flags(referenceProperty.isIdProperty() ? Collections.singletonList(ResultFlag.ID) : Collections.emptyList())
                                            .typeHandler(typeHandler)
                                            .build();
                                })
                                .collect(Collectors.toList());

//                        final String associationId = String.format("resultMap[%s].association[%s]", entity.getType().getSimpleName(), property.getName());


//                        final ResultMap association = new ResultMap.Builder(configuration, associationId, type, composites)
//                                .build();

//                        if (!configuration.hasResultMap(associationId)) {
//                            configuration.addResultMap(association);
//                        }


                        final String name = property.getName();
                        return new ResultMapping.Builder(configuration, name)
//                                .column(property.getColumn())
                                .javaType(type)
                                .flags(property.isIdProperty() ? Collections.singletonList(ResultFlag.ID) : Collections.emptyList())
                                .composites(composites)
//                                .nestedResultMapId(associationId)
                                .typeHandler(configuration.getTypeHandlerRegistry().getUnknownTypeHandler())
                                .build();


                    } else {
                        final String name = property.getName();
                        final String column = property.getColumn();
                        final TypeHandler<?> typeHandler = findTypeHandler(property);

                        return new ResultMapping.Builder(configuration, name, column, type)
                                .flags(property.isIdProperty() ? Collections.singletonList(ResultFlag.ID) : Collections.emptyList())
                                .typeHandler(typeHandler)
                                .build();
                    }

                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());


        this.resultMap = new ResultMap.Builder(configuration, id, entity.getType(), resultMappings)
                .build();

        if (configuration != null && !configuration.hasResultMap(id)) {
            configuration.addResultMap(resultMap);
        }

    }

    public static void main(String[] args) {
        System.out.println(new ResultMapHolder(new Configuration(), Entity.from(AbsEntity.class)).resultMap);
    }

    private TypeHandler<?> findTypeHandler(Property property) {

        if (property.isCollectionLike() || property.isMap()) {
            return new TypeReferenceJsonTypeHandler<>(property.getField().getGenericType());
        }

        return configuration.getTypeHandlerRegistry().getTypeHandler(property.getType());
    }

    public ResultMap getResultMap() {
        return resultMap;
    }


}

