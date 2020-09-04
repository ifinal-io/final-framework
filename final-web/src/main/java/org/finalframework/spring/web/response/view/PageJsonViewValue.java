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

package org.finalframework.spring.web.response.view;


import com.fasterxml.jackson.annotation.JsonView;
import com.github.pagehelper.PageInfo;
import org.finalframework.data.annotation.result.Page;
import org.finalframework.json.jackson.view.JsonViewValue;

import java.io.Serializable;

/**
 * @author likly
 * @version 1.0
 * @date 2019-12-09 10:57:58
 * @see JsonView
 * @see JsonViewValue
 * @see com.github.pagehelper.Page
 * @since 1.0
 */
public class PageJsonViewValue<T extends Serializable> extends Page<JsonViewValue> {

    private static final long serialVersionUID = -7123032399172554094L;

    public PageJsonViewValue(com.github.pagehelper.Page<T> page, Class<?> view) {
        final PageInfo<T> info = page.toPageInfo();

        this.setPage(info.getPageNum());
        this.setSize(info.getSize());
        this.setPages(info.getPages());
        this.setTotal(info.getTotal());
        this.setFirstPage(info.isIsFirstPage());
        this.setLastPage(info.isIsLastPage());
        this.setData(new JsonViewValue(page.getResult(), view));
    }
}

