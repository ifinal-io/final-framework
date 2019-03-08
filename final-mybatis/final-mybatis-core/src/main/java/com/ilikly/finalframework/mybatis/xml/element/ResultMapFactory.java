package com.ilikly.finalframework.mybatis.xml.element;

import com.ilikly.finalframework.core.Factory;
import com.ilikly.finalframework.data.annotation.enums.ReferenceMode;
import com.ilikly.finalframework.data.mapping.Dialect;
import com.ilikly.finalframework.data.mapping.Entity;
import com.ilikly.finalframework.data.mapping.converter.NameConverterRegistry;
import com.ilikly.finalframework.mybatis.Utils;
import org.apache.ibatis.type.TypeHandler;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-22 13:49:59
 * @since 1.0
 */
public class ResultMapFactory implements Factory {

    private static final String SUFFIX = "Map";

    @NonNull
    public ResultMap create(@NonNull Class<?> clazz) {
        final Entity<?> entity = Entity.from(clazz);
        final ResultMap.Builder builder = ResultMap.builder(clazz.getSimpleName() + SUFFIX, clazz);
        entity.stream().filter(it -> !it.isTransient())
                .forEach(property -> {
                    if (property.isReference()) {
                        final Class<?> javaType = Utils.getPropertyJavaType(property);
                        final Entity<?> multiEntity = Entity.from(javaType);
                        final Association.Builder assocation = Association.builder(property.getName())
                                .javaType(javaType);

                        property.referenceProperties().stream()
                                .map(String::trim)
                                .map(multiEntity::getRequiredPersistentProperty)
                                .map(multiProperty -> {
                                    final Class multiPropertyJavaType = Utils.getPropertyJavaType(multiProperty);
                                    final TypeHandler typeHandler = Utils.getPropertyTypeHandler(Dialect.DEFAULT, multiProperty);
                                    final String column = property.isIdProperty() && property.referenceMode() == ReferenceMode.SIMPLE ?
                                            property.getColumn() : property.getColumn() + multiProperty.getColumn().substring(0, 1).toUpperCase() + multiProperty.getColumn().substring(1);
                                    return Result.builder(multiProperty.getName(), NameConverterRegistry.getInstance().getColumnNameConverter().convert(column))
                                            .javaType(multiPropertyJavaType)
                                            .typeHandler(typeHandler)
                                            .idResult(multiProperty.isIdProperty())
                                            .build();
                                })
                                .forEach(assocation::addResult);

                        builder.addAssociation(assocation.build());

                    } else {
                        builder.addResult(
                                Result.builder(property.getName(), NameConverterRegistry.getInstance().getColumnNameConverter().convert(property.getColumn()))
                                        .idResult(property.isIdProperty())
                                        .javaType(Utils.getPropertyJavaType(property))
                                        .typeHandler(Utils.getPropertyTypeHandler(Dialect.DEFAULT, property))
                                        .build()
                        );
                    }
                });

        return builder.build();
    }
}
