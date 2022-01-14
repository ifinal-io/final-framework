package org.ifinalframework.poi.mapping;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.ifinalframework.poi.TypeHandler;

/**
 * @author likly
 * @version 1.2.4
 **/
@Setter
@Getter
@RequiredArgsConstructor
public class ResultMapping {
    private Integer index;
    private final String property;
    private final String column;
    private final TypeHandler<?> typeHandler;
}
