/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.finalframework.data.annotation.query;

import org.springframework.lang.Nullable;

/**
 * 可分页的
 *
 * @author likly
 * @version 1.0
 * @date 2019-02-18 19:21:21
 * @since 1.0
 */
public interface Pageable extends IQuery {
    /**
     * 返回分页页码
     *
     * @return 分页页码
     */
    @Nullable
    Integer getPage();

    /**
     * 返回页面容量
     *
     * @return 页面容量
     */
    @Nullable
    Integer getSize();

    /**
     * 是否进行Count统计
     *
     * @return Count统计
     */
    @Nullable
    default Boolean getCount() {
        return Boolean.TRUE;
    }

}
