package org.ifinal.finalframework.data.poi.excel;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import org.ifinal.finalframework.annotation.poi.excel.DataType;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

/**
 * Excel.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Setter
@Getter
public class Excel {

    private Version version;

    private String password;

    private List<Sheet> sheets;

    private List<Style> styles;

    @Setter
    @Getter
    public static class Sheet {

        @Nullable
        private String name;

        @Nullable
        private Float defaultColumnWidth;

        @Nullable
        private Float defaultRowHeight;

        private List<Row> headers;

        private Row body;

        private List<Row> footers;

    }

    @Setter
    @Getter
    public static class Row {

        @NonNull
        private List<Cell> cells;

        @Nullable
        private Float height;

        @Nullable
        private String style;

        public Row() {
        }

        public Row(final List<Cell> cells) {
            this.cells = cells;
        }

    }

    /**
     * Style.
     *
     * @author likly
     * @version 1.0.0
     * @since 1.0.0
     */
    @Setter
    @Getter
    public static class Style {

        private String title;

        private Boolean locked;

        private HorizontalAlignment horizontalAlignment;

        private VerticalAlignment verticalAlignment;

        private String foregroundColor;

        private Font font;

    }

    @Data
    public static class Font {

        private String name;
        private Short size;
        private String color;

    }

    /**
     * Cell.
     *
     * @author likly
     * @version 1.0.0
     * @since 1.0.0
     */
    @Setter
    @Getter
    public static class Cell {

        private Integer index;

        @NonNull
        private String value;

        @Nullable
        private Integer columnWidth;

        @Nullable
        private String style;

        @Nullable
        private DataType type = DataType.AUTO;

        public Cell() {
        }

        public Cell(@NonNull final String value) {
            this.value = value;
        }

    }

    public enum Version {
        XLS, XLSX
    }

}
