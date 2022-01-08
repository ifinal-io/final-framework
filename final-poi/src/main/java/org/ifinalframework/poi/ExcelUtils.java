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

    public static Excel.Style merge(Excel.Style... styles) {

        Excel.Style mergedStyle = new Excel.Style();

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
