package org.ifinalframework.poi;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * @author likly
 * @version 1.2.4
 **/
public interface Handler<T> {

    default void startSheet(int sheetIndex, String sheetName, Sheet sheet) {

    }

    T startRow(int sheetIndex, String sheetName, int rowIndex, Row row);

    default void endRow(int sheetIndex, String sheetName, int rowIndex, Row row) {

    }

    default void endSheet(int sheetIndex, String sheetName, Sheet sheet) {

    }
}
