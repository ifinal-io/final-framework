package org.ifinalframework.poi.handler;

import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.ifinalframework.poi.Handler;
import org.ifinalframework.poi.TypeHandler;
import org.ifinalframework.poi.TypeHandlerRegistry;
import org.ifinalframework.poi.mapping.ResultMap;
import org.ifinalframework.poi.mapping.ResultMapping;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.2.4
 **/
public class ResultMapHandler<T> implements Handler<T> {
    private final TypeHandlerRegistry registry;
    private final int headerRowIndex;
    private final ResultMap<T> resultMap;

    private final Map<String,Integer> columnIndexs = new LinkedHashMap<>();
    private final Map<String, PropertyDescriptor> propertyDescriptors;


    public ResultMapHandler(int headerRowIndex,ResultMap<T> resultMap){
        this(new TypeHandlerRegistry(),headerRowIndex,resultMap);
    }

    @SneakyThrows
    public ResultMapHandler(TypeHandlerRegistry registry, int headerRowIndex, ResultMap<T> resultMap) {
        this.registry = registry;
        this.headerRowIndex = headerRowIndex;
        this.resultMap = resultMap;

        BeanInfo beanInfo = Introspector.getBeanInfo(resultMap.getJavaType());
        propertyDescriptors =  Arrays.stream(beanInfo.getPropertyDescriptors())
                .collect(Collectors.toMap(PropertyDescriptor::getName, Function.identity()));


    }

    @Override
    @SneakyThrows
    public T startRow(int sheetIndex, String sheetName, int rowIndex, Row row) {
        if(rowIndex < headerRowIndex){
            return null;
        }

        if(rowIndex == headerRowIndex){
            int lastCellNum = row.getLastCellNum();
            for (int index = 0; index < lastCellNum; index++) {
                columnIndexs.put(row.getCell(index).getStringCellValue(),index);
            }
            return null;
        }



        T instance = resultMap.getJavaType().newInstance();

        for (ResultMapping resultMapping : resultMap.getResultMappings()) {

            Integer index = resultMapping.getIndex();

            if(Objects.isNull(index)){
                index = columnIndexs.get(resultMapping.getColumn());
            }

            Cell cell = row.getCell(index);

            PropertyDescriptor descriptor = propertyDescriptors.get(resultMapping.getProperty());
            Class<?> javaType = descriptor.getPropertyType();

            TypeHandler<?> typeHandler = registry.getTypeHandler(javaType);
            Object value = typeHandler.getValue(cell);
            descriptor.getWriteMethod().invoke(instance,value);


        }


        return instance;

    }
}
