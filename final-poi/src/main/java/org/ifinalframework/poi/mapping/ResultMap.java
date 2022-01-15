package org.ifinalframework.poi.mapping;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * @author likly
 * @version 1.2.4
 **/
@Setter
@Getter
@AllArgsConstructor
public class ResultMap<T> {
    private Class<T> javaType;
    private List<ResultMapping> resultMappings;

    public boolean isMap(){
        return Map.class.isAssignableFrom(javaType);
    }
}
