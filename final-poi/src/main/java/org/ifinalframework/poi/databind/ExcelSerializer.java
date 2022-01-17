package org.ifinalframework.poi.databind;

import org.apache.poi.ss.usermodel.Cell;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.2.4
 **/
public interface ExcelSerializer<T> {
    void serialize(@NonNull Cell cell, @Nullable T value);
}
