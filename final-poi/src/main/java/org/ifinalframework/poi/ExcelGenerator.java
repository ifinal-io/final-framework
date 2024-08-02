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

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import org.ifinalframework.poi.model.MergedRegion;
import org.ifinalframework.poi.model.Style;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * {@code Excel}生成器。
 *
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public interface ExcelGenerator {

    /**
     * 创建单元格样式
     *
     * @param style 样式
     * @return 单元格样式
     */
    @NonNull
    CellStyle createCellStyle(@NonNull Style style);

    /**
     * 创建字体
     *
     * @param font 字体
     * @return 字体
     */
    @NonNull
    Font createFont(@NonNull org.ifinalframework.poi.model.Font font);

    /**
     * 创建{@code sheet}
     *
     * @param sheet sheet
     * @return sheet
     */
    @NonNull
    Sheet createSheet(@NonNull org.ifinalframework.poi.model.Sheet sheet);

    /**
     * 创建行{@code row}
     *
     * @param sheet 表
     * @param row   行
     * @return 行
     */
    @NonNull
    Row createRow(@NonNull Sheet sheet, @NonNull org.ifinalframework.poi.model.Row row);

    /**
     * 创建单元格
     *
     * @param row  行
     * @param cell 单元格
     * @return 单元格
     */
    @NonNull
    Cell createCell(@NonNull Row row, @NonNull org.ifinalframework.poi.model.Cell cell);

    @Nullable
    CellStyle getCellStyle(@Nullable String style);

    @NonNull
    Sheet getSheet(int index);

    /**
     * 向第一个{@code sheet}写一行数据
     *
     * @param row  行
     * @param data 数据
     * @see #writeRow(int, org.ifinalframework.poi.model.Row, Object)
     */
    default void writeRow(@NonNull org.ifinalframework.poi.model.Row row, @Nullable Object data) {
        writeRow(0, row, data);
    }

    /**
     * 向第{@code index}个{@code sheet}写一行数据
     *
     * @param index sheet索引
     * @param row   行
     * @param data  数据
     */
    void writeRow(int index, @NonNull org.ifinalframework.poi.model.Row row, @Nullable Object data);

    /**
     * 向{@code sheet}中写一行数据
     *
     * @param sheet sheet
     * @param row   行
     * @param data  数据
     */
    void writeRow(@NonNull Sheet sheet, @NonNull org.ifinalframework.poi.model.Row row, @Nullable Object data);

    void writeCell(@NonNull Cell rowCell, @NonNull org.ifinalframework.poi.model.Cell cell, @Nullable Object data);

    /**
     * 向第1个{@code sheet}添加一个合并单元格
     * @param mergedRegions 合并单元格
     */
    default void addMergedRegions(List<MergedRegion> mergedRegions) {
        addMergedRegions(0, mergedRegions);
    }

    /**
     * 向第{@code index}个{@code sheet}添加一个合并单元格
     * @param index sheet索引
     * @param mergedRegions 合并单元格
     */
    void addMergedRegions(int index, List<MergedRegion> mergedRegions);

    /**
     * 写到输出流
     * @param os 输出流
     * @throws IOException IO异常
     */
    void write(@NonNull OutputStream os) throws IOException;

    /**
     * 写到文件
     * @param filename 文件名
     * @throws IOException IO异常
     */
    default void write(@NonNull String filename) throws IOException {
        write(new FileOutputStream(filename));
    }

}
