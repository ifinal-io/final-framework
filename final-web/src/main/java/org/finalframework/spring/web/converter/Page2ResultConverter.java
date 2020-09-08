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

package org.finalframework.spring.web.converter;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.finalframework.core.converter.Converter;
import org.finalframework.data.annotation.result.Pagination;
import org.finalframework.data.annotation.result.R;
import org.finalframework.data.annotation.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * @author likly
 * @version 1.0
 * @date 2020-07-21 17:25:31
 * @since 1.0
 */
public class Page2ResultConverter<T extends Serializable> implements Converter<Page<T>, Result<ArrayList<T>>> {

    public static final Logger logger = LoggerFactory.getLogger(Page2ResultConverter.class);

    @Override
    public Result<ArrayList<T>> convert(Page<T> source) {
        final Result<ArrayList<T>> result = R.success(new ArrayList<>(source.getResult()));
        result.setPagination(buildPageInfo(source));
        return result;
    }

    private Pagination buildPageInfo(Page<T> page) {
        final PageInfo<T> pageInfo = page.toPageInfo();
        final Pagination result = new Pagination();
        result.setPage(pageInfo.getPageNum());
        result.setSize(pageInfo.getSize());
        result.setPages(pageInfo.getPages());
        result.setTotal(pageInfo.getTotal());
        result.setFirstPage(pageInfo.isIsFirstPage());
        result.setLastPage(pageInfo.isIsLastPage());
        return result;


    }
}
