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

import lombok.Getter;
import lombok.Setter;

/**
 * 合并单元格
 *
 * @author iimik
 * @since 1.5.2
 **/
@Getter
@Setter
public class MergedRegion {
    /**
     * 行
     */
    private Integer row;
    /**
     * 列
     */
    private Integer colum;
    /**
     * 宽
     */
    private Integer width;
    /**
     * 高
     */
    private Integer height;
}
