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

import org.ifinalframework.core.lang.SpEL;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Excel.
 *
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
@Setter
@Getter
public class Excel {

    /**
     * 生成的Excel文件名
     */
    @SpEL
    private String name;

    /**
     * Excel 版本
     */
    private Version version;

    /**
     * 生成的Excel文件访问密码
     */
    private String password;

    /**
     * Excel Sheet列表
     */
    private List<Sheet> sheets;

    /**
     * 风格列表
     */
    private List<Style> styles;

}
