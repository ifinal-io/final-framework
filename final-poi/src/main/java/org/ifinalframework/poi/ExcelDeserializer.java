package org.ifinalframework.poi;

import org.apache.poi.ss.usermodel.Cell;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.2.4
 **/
public interface ExcelDeserializer<T> {
    @Nullable
    T deserialize(@NonNull Cell cell);
}
