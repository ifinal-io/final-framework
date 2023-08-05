/*
 * Copyright 2020-2022 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ifinalframework.poi;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * Excel.
 *
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
@Setter
@Getter
public class Excel {

    private String name;

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

        private List<MergedRegion> mergedRegions;

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
     * @author ilikly
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

        private String dataFormat;

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
     * @author ilikly
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

        public Cell() {
        }

        public Cell(@NonNull final String value) {
            this.value = value;
        }

    }

    @Getter
    @Setter
    public static class MergedRegion {
        private Integer row;
        private Integer colum;
        private Integer width;
        private Integer height;
    }

    public enum Version {
        XLS, XLSX
    }

}
