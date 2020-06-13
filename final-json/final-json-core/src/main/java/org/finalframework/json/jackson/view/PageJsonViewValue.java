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

package org.finalframework.json.jackson.view;


import com.fasterxml.jackson.annotation.JsonView;
import org.finalframework.data.entity.PageImpl;
import org.finalframework.data.result.Page;

import java.io.Serializable;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2019-12-09 10:57:58
 * @see JsonView
 * @see JsonViewValue
 * @see Page
 * @since 1.0
 */
public class PageJsonViewValue<T extends Serializable> extends PageImpl<JsonViewValue<List<T>>> {

    private static final long serialVersionUID = -7123032399172554094L;

    public PageJsonViewValue(Page<T> page, Class<?> view) {
        this.setPage(page.getPage());
        this.setSize(page.getSize());
        this.setPages(page.getPages());
        this.setTotal(page.getTotal());
        this.setFirstPage(page.getFirstPage());
        this.setLastPage(page.getLastPage());
        this.setResult(new JsonViewValue<>(page.getResult(), view));
    }
}

