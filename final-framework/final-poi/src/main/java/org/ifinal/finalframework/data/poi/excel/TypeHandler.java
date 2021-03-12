package org.ifinal.finalframework.data.poi.excel;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import org.apache.poi.ss.usermodel.Cell;

/**
 * TypeHandler.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface TypeHandler<T> {

    void setValue(@NonNull Cell cell, @Nullable T value);

}
