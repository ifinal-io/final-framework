package org.ifinal.finalframework.data.poi.excel;

import org.ifinal.finalframework.data.poi.excel.Excel.Style;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * ExcelUtils.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public final class ExcelUtils {

    private static final Integer LOOP = 26;

    private ExcelUtils() {
    }

    public static String columnLabel(int index) {

        StringBuilder sb = new StringBuilder();

        int offset = -1;

        do {

            if (offset != -1) {
                index--;
            }

            offset = index % LOOP;

            sb.insert(0, (char) ('A' + offset));
            index /= LOOP;

        } while (index > 0);
        return sb.toString();

    }

    public static String rowLabel(int index) {
        return String.valueOf(index + 1);
    }

    public static String cellLabel(int rowIndex, int columnIndex) {
        return columnLabel(columnIndex) + rowLabel(rowIndex);
    }


    public static Style merge(Style... styles) {

        Style mergedStyle = new Style();

        Arrays.stream(styles)
            .filter(Objects::nonNull)
            .forEach(style -> {
                Optional.ofNullable(style.getLocked()).ifPresent(mergedStyle::setLocked);
                Optional.ofNullable(style.getHorizontalAlignment()).ifPresent(mergedStyle::setHorizontalAlignment);
                Optional.ofNullable(style.getVerticalAlignment()).ifPresent(mergedStyle::setVerticalAlignment);
            });

        return mergedStyle;

    }


}
