/*
 * Copyright 2020-2021 the original author or authors.
 *
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
 *
 */

package org.ifinalframework.core.result;

import org.ifinalframework.core.IPagination;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <pre class="code">
 * {
 *     "page": 1,
 *     "size": 20,
 *     "pages": 5,
 *     "total": 100,
 *     "firstPage": true,
 *     "lastPage": true
 * }
 * </pre>
 *
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
@Setter
@Getter
@ToString
public class Pagination implements IPagination {

    private static final long serialVersionUID = -4875155337971995663L;

    /**
     * 页码
     */
    private Integer page;

    /**
     * 页面容量
     */
    private Integer size;

    /**
     * 页数
     */
    private Integer pages;

    /**
     * 总条数
     */
    private Long total;

    /**
     * 是否首页
     */
    private Boolean firstPage;

    /**
     * 是否尾页
     */
    private Boolean lastPage;

}

