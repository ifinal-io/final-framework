package org.ifinal.finalframework.data.poi.excel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * WorkbookWriter.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface WorkbookWriter {

    WorkbookWriter append(int sheetIndex, List<?> rows);

    default WorkbookWriter append(List<?> rows) {
        return append(0, rows);
    }

    void write(OutputStream os) throws IOException;

    default void write(String filename) throws IOException {
        write(new FileOutputStream(filename));
    }

}
