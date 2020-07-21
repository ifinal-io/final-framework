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

/**
 * 将 {@link Page}对象转化成 {@link org.finalframework.data.result.Page}对象的转换器
 *
 * @author likly
 * @version 1.0
 * @date 2019-09-24 23:52:24
 * @since 1.0
 */
@Deprecated
public class Page2PageConverter implements Converter<Page<?>, org.finalframework.data.result.Page<?>> {
    @Override
    public org.finalframework.data.result.Page<?> convert(Page<?> source) {
        final PageInfo pageInfo = source.toPageInfo();
        return org.finalframework.data.result.Page.builder()
                .page(pageInfo.getPageNum())
                .size(pageInfo.getPageSize())
                .pages(pageInfo.getPages())
                .total(pageInfo.getTotal())
                .result(pageInfo.getList())
                .firstPage(pageInfo.isIsFirstPage())
                .lastPage(pageInfo.isIsLastPage())
                .build();
    }
}
