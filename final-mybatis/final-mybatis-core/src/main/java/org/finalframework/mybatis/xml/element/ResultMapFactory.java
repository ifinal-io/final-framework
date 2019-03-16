package org.finalframework.mybatis.xml.element;

import org.apache.ibatis.type.TypeHandler;
import org.finalframework.core.Factory;
import org.finalframework.data.annotation.enums.ReferenceMode;
import org.finalframework.data.mapping.Entity;
import org.finalframework.data.mapping.converter.NameConverterRegistry;
import org.finalframework.mybatis.Utils;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-22 13:49:59
 * @since 1.0
 */
public class ResultMapFactory implements Factory {

    private static final String SUFFIX = "Map";

    public ResultMap create(@NonNull Entity<?> entity) {
        final ResultMap.Builder builder = ResultMap.builder(entity.getType().getSimpleName() + SUFFIX, entity.getType());
        entity.stream().filter(it -> !it.isTransient())
                .forEach(property -> {
                    if (property.isReference()) {
                        final Class<?> javaType = Utils.getPropertyJavaType(property);
                        final Entity<?> referenceEntity = Entity.from(javaType);
                        final Association.Builder assocation = Association.builder(property.getName())
                                .javaType(javaType);

                        property.referenceProperties().stream()
                                .map(String::trim)
                                .map(referenceEntity::getRequiredPersistentProperty)
                                .map(referenceProperty -> {
                                    final Class multiPropertyJavaType = Utils.getPropertyJavaType(referenceProperty);
                                    final TypeHandler typeHandler = Utils.getPropertyTypeHandler(referenceProperty);

                                    final String referenceColumn = property.referenceColumn(referenceProperty.getName()) != null ?
                                            property.referenceColumn(referenceProperty.getName()) : referenceProperty.getColumn();

                                    final String column = referenceProperty.isIdProperty() && property.referenceMode() == ReferenceMode.SIMPLE ?
                                            property.getColumn() : property.getColumn() + referenceColumn.substring(0, 1).toUpperCase() + referenceColumn.substring(1);

                                    return Result.builder(referenceProperty.getName(), NameConverterRegistry.getInstance().getColumnNameConverter().convert(column))
                                            .javaType(multiPropertyJavaType)
                                            .typeHandler(typeHandler)
                                            .idResult(referenceProperty.isIdProperty())
                                            .build();
                                })
                                .forEach(assocation::addResult);

                        builder.addAssociation(assocation.build());

                    } else {
                        builder.addResult(
                                Result.builder(property.getName(), NameConverterRegistry.getInstance().getColumnNameConverter().convert(property.getColumn()))
                                        .idResult(property.isIdProperty())
                                        .javaType(Utils.getPropertyJavaType(property))
                                        .typeHandler(Utils.getPropertyTypeHandler(property))
                                        .build()
                        );
                    }
                });

        return builder.build();
    }

}
