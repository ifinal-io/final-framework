/*
 * Copyright 2020-2023 the original author or authors.
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

package org.ifinalframework.poi.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Sheet
 *
 * @author iimik
 * @since 1.5.2
 **/
@Setter
@Getter
public class Sheet {

    /**
     * sheet 名称
     */
    private String name;

    /**
     * 默认列宽
     */
    private Float defaultColumnWidth;

    /**
     * 默认列高
     */
    private Float defaultRowHeight;

    /**
     * 表头配置
     */
    private List<Row> headers;

    /**
     * 内容配置
     */
    private Row body;

    /**
     * 表脚配置
     */
    private List<Row> footers;

    /**
     * 合并单元格
     */
    private List<MergedRegion> mergedRegions;

}
