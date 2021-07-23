/*
 * Copyright 2020-2021 the original author or authors.
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

package org.ifinalframework.context.result.consumer;

import org.springframework.stereotype.Component;

import org.ifinalframework.context.result.ResultConsumer;
import org.ifinalframework.core.result.Pagination;
import org.ifinalframework.core.result.Result;

import java.util.Objects;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

/**
 * PageResultConsumer.
 *
 * @author likly
 * @version 1.2.1
 * @since 1.2.1
 */
@Component
public class PageResultConsumer implements ResultConsumer<Page<?>> {

    @Override
    public void accept(Result<Page<?>> result) {
        final Page<?> page = Objects.requireNonNull(result.getData());
        result.setPagination(buildPageInfo(page));
    }

    private Pagination buildPageInfo(final Page<?> page) {
        final PageInfo<?> pageInfo = page.toPageInfo();
        final Pagination result = new Pagination();
        result.setPage(pageInfo.getPageNum());
        result.setSize(pageInfo.getPageSize());
        result.setPages(pageInfo.getPages());
        result.setTotal(pageInfo.getTotal());
        result.setFirstPage(pageInfo.isIsFirstPage());
        result.setLastPage(pageInfo.isIsLastPage());
        return result;
    }

    @Override
    public boolean test(Result<?> result) {
        return Objects.nonNull(result.getData()) && result.getData() instanceof Page;
    }

}
