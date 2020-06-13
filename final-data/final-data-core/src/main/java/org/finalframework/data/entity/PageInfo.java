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

package org.finalframework.data.entity;


import lombok.Data;

import java.io.Serializable;

/**
 * @author likly
 * @version 1.0
 * @date 2019-12-06 17:00:44
 * @since 1.0
 */
@Data
public class PageInfo implements Serializable {
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

    public PageInfo toPageInfo() {
        PageInfo pageInfo = new PageInfo();
        pageInfo.page = this.page;
        pageInfo.size = this.size;
        pageInfo.pages = this.pages;
        pageInfo.total = this.total;
        pageInfo.firstPage = this.firstPage;
        pageInfo.lastPage = this.lastPage;
        return pageInfo;
    }
}

