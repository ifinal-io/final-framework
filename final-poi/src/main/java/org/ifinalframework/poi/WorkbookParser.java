package org.ifinalframework.poi;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.lang.NonNull;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


/**
 * @author likly
 * @version 1.2.4
 **/
@RequiredArgsConstructor
public class WorkbookParser {


    public <T> List<T> parse(Workbook workbook, int sheetIndex, Handler<T> handler) {
        Sheet sheet = Objects.requireNonNull(workbook.getSheetAt(sheetIndex), "can not fount sheet as index of " + sheetIndex);
        return parse(sheetIndex, sheet.getSheetName(), sheet, handler);
    }

    public <T> List<T> parse(Workbook workbook,String sheetName,Handler<T> handler){
        Sheet sheet = Objects.requireNonNull(workbook.getSheet(sheetName), "can not found sheet of name " + sheetName);
        return parse(workbook.getSheetIndex(sheet),sheetName,sheet,handler);
    }

    private <T> List<T> parse( int sheetIndex, String sheetName, Sheet sheet, Handler<T> handler) {
        handler.startSheet(sheetIndex, sheetName, sheet);
        List<T> sheetList = new LinkedList<>();
        for (int rowIndex = 0; rowIndex < sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            T t = handler.startRow(sheetIndex, sheetName, rowIndex, row);
            if(Objects.nonNull(t)) {
                sheetList.add(t);
            }
            handler.endRow(sheetIndex, sheetName, rowIndex, row);
        }
        return sheetList;
    }


    public <T> List<List<T>> parse(Workbook workbook, @NonNull Handler<T> handler) {
        int numberOfSheets = workbook.getNumberOfSheets();
        List<List<T>> sheetLists = new LinkedList<>();
        for (int sheetIndex = 0; sheetIndex < numberOfSheets; sheetIndex++) {
            final Sheet sheet = workbook.getSheetAt(sheetIndex);
            final String sheetName = sheet.getSheetName();
            List<T> list = parse(sheetIndex, sheetName, sheet, handler);
            sheetLists.add(list);

            handler.endSheet(sheetIndex, sheetName, sheet);
        }
        return sheetLists;
    }

}