package org.ifinal.finalframework.annotation.poi.excel;

import org.apache.poi.ss.usermodel.Cell;

/**
 * TypeHandler.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface TypeHandler<T> {

    void setValue(Cell cell, T value);

}
