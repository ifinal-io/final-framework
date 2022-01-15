package org.ifinalframework.poi.function;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.ifinalframework.poi.Headers;
import org.ifinalframework.poi.TypeHandler;
import org.ifinalframework.poi.type.ObjectTypeHandler;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

/**
 * @author likly
 * @version 1.2.4
 **/
public class MapBiFunction implements BiFunction<Row, Headers, Map<String, Object>> {
    private final TypeHandler<Object> typeHandler = new ObjectTypeHandler();

    @Override
    public Map<String, Object> apply(Row row, Headers headers) {

        final Map<String, Object> map = new LinkedHashMap<>();

        headers.foreach((header, index) -> {
            Cell cell = row.getCell(index);
            Object value = Optional.ofNullable(cell).map(typeHandler::getValue).orElse(null);
            map.put(header, value);
        });

        return  map;
    }
}
