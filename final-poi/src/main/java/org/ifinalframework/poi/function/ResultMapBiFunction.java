package org.ifinalframework.poi.function;

import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.ifinalframework.poi.Headers;
import org.ifinalframework.poi.TypeHandler;
import org.ifinalframework.poi.TypeHandlerRegistry;
import org.ifinalframework.poi.mapping.ResultMap;
import org.ifinalframework.poi.mapping.ResultMapping;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.2.4
 **/
public class ResultMapBiFunction<T> implements BiFunction<Row, Headers, T> {

    private final TypeHandlerRegistry registry;
    private final ResultMap<T> resultMap;
    private final Map<String, PropertyDescriptor> propertyDescriptors;

    public ResultMapBiFunction(ResultMap<T> resultMap) {
        this(new TypeHandlerRegistry(), resultMap);
    }

    @SneakyThrows
    public ResultMapBiFunction(TypeHandlerRegistry registry, ResultMap<T> resultMap) {
        this.registry = registry;
        this.resultMap = resultMap;

        if(resultMap.isMap()){
            propertyDescriptors = null;
        }else {
            BeanInfo beanInfo = Introspector.getBeanInfo(resultMap.getJavaType());
            propertyDescriptors = Arrays.stream(beanInfo.getPropertyDescriptors())
                    .collect(Collectors.toMap(PropertyDescriptor::getName, Function.identity()));
        }
    }

    @Override
    @SneakyThrows
    public T apply(Row row, Headers headers) {

        if(resultMap.isMap()){
            Map<String,Object> map = new LinkedHashMap<>();

            headers.foreach((header,index) -> {
                Cell cell = row.getCell(index);
                TypeHandler<Object> typeHandler = registry.getTypeHandler(Object.class);
                Object value = Optional.ofNullable(cell).map(typeHandler::getValue).orElse(null);
                map.put(header,value);
            });


            return (T) map;
        }


        T instance = resultMap.getJavaType().newInstance();

        for (ResultMapping resultMapping : resultMap.getResultMappings()) {

            Integer index = resultMapping.getIndex();

            if (Objects.isNull(index)) {
                index = headers.getHeaderIndex(resultMapping.getColumn());
            }

            Cell cell = row.getCell(index);

            PropertyDescriptor descriptor = propertyDescriptors.get(resultMapping.getProperty());
            Class<?> javaType = descriptor.getPropertyType();

            TypeHandler<?> typeHandler = registry.getTypeHandler(javaType);
            Object value = Optional.ofNullable(cell).map(typeHandler::getValue).orElse(null);
            descriptor.getWriteMethod().invoke(instance, value);


        }


        return instance;
    }
}
