/*
 * Copyright 2020-2022 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ifinalframework.poi.mapping;

import lombok.SneakyThrows;
import org.ifinalframework.poi.annotaion.ExcelColumn;
import org.ifinalframework.poi.annotaion.ExcelDeserialize;
import org.ifinalframework.poi.annotaion.ExcelSerialize;
import org.ifinalframework.poi.databind.ExcelDeserializer;
import org.ifinalframework.poi.databind.ExcelSerializer;
import org.ifinalframework.poi.databind.TypeHandler;
import org.ifinalframework.poi.databind.TypeHandlerRegistry;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.NonNull;
import org.springframework.util.ReflectionUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.2.4
 **/
public class DefaultResultMapFactory implements ResultMapFactory {

    private final TypeHandlerRegistry typeHandlerRegistry = new TypeHandlerRegistry();
    private final Map<Class<?>, ResultMap<?>> cache = new LinkedHashMap<>();

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T> ResultMap<T> create(@NonNull Class<T> clazz) {
        Objects.requireNonNull(clazz, "clazz can not be null.");
        return (ResultMap<T>) cache.computeIfAbsent(clazz, this::build);
    }

    private <T> ResultMap<T> build(Class<T> clazz) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);

            List<ResultMapping> resultMappings = Arrays.stream(beanInfo.getPropertyDescriptors())
                    .filter(it -> Objects.nonNull(it.getWriteMethod()))
                    .map(property -> this.build(clazz, property))
                    .collect(Collectors.toList());

            return new ResultMap<>(clazz, resultMappings);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    private ResultMapping build(Class<?> clazz, PropertyDescriptor property) {
        String name = property.getName();
        Field field = Objects.requireNonNull(ReflectionUtils.findField(clazz, name));
        ExcelColumn excelColumn = AnnotationUtils.findAnnotation(field, ExcelColumn.class);

        final String column = Objects.isNull(excelColumn) ? name : excelColumn.header();

        ExcelDeserialize excelDeserialize = AnnotationUtils.findAnnotation(field, ExcelDeserialize.class);
        ExcelSerialize excelSerialize = AnnotationUtils.findAnnotation(field, ExcelSerialize.class);


        TypeHandler<?> typeHandler = typeHandlerRegistry.getTypeHandler(field.getType());

        ExcelDeserializer deserializer = Objects.isNull(excelDeserialize) ? typeHandler : excelDeserialize.use().newInstance();

        ExcelSerializer serializer = Objects.isNull(excelSerialize) ? typeHandler : excelSerialize.use().newInstance();

        return ResultMapping.builder()
                .property(name)
                .column(column)
                .deserializer(deserializer)
                .serializer(serializer)
                .build();
    }


}
