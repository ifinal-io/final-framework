package org.ifinal.finalframework.annotation.poi.excel;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DataType.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum DataType {
    AUTO, STRING, NUMBER, EXPRESSION,FORMULA
}
