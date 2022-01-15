package org.ifinalframework.poi.function;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.ifinalframework.poi.Headers;
import org.ifinalframework.poi.TypeHandler;
import org.ifinalframework.poi.mapping.ResultMap;
import org.ifinalframework.poi.mapping.ResultMapping;
import org.ifinalframework.poi.type.ObjectTypeHandler;
import org.springframework.beans.BeanWrapperImpl;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;

/**
 * @author likly
 * @version 1.2.4
 **/
@RequiredArgsConstructor
public class ResultMapBiFunction<T> implements BiFunction<Row, Headers, T> {

    private final ResultMap<T> resultMap;

    @Override
    @SneakyThrows
    @SuppressWarnings("unchecked")
    public T apply(Row row, Headers headers) {

        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(resultMap.getJavaType());

        for (ResultMapping resultMapping : resultMap.getResultMappings()) {

            Integer index = resultMapping.getIndex();

            if (Objects.isNull(index)) {
                index = headers.getHeaderIndex(resultMapping.getColumn());
            }

            Cell cell = row.getCell(index);

            if (Objects.isNull(cell)) {
                continue;
            }

            TypeHandler<?> typeHandler = resultMapping.getTypeHandler();
            Object value = typeHandler.getValue(cell);
            beanWrapper.setPropertyValue(resultMapping.getProperty(), value);

        }

        return (T) beanWrapper.getWrappedInstance();

    }
}
