package org.ifinalframework.poi.mapping;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
}
