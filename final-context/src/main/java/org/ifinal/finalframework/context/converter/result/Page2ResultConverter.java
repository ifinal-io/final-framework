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

package org.ifinal.finalframework.context.converter.result;

import org.ifinal.finalframework.core.result.Pagination;
import org.ifinal.finalframework.core.result.R;
import org.ifinal.finalframework.core.result.Result;
import org.ifinal.finalframework.util.function.Converter;

import java.util.ArrayList;
import java.util.Objects;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * Wrap the {@link Page} by {@link Result} with {@link Pagination}.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
public class Page2ResultConverter<T> implements Converter<Page<T>, Result<ArrayList<T>>> {

    @Override
    public Result<ArrayList<T>> convert(final Page<T> source) {

        if (Objects.isNull(source)) {
            return R.success();
        }

        final Result<ArrayList<T>> result = R.success(new ArrayList<>(source.getResult()));
        result.setPagination(buildPageInfo(source));
        return result;
    }

    private Pagination buildPageInfo(final Page<T> page) {
        final PageInfo<T> pageInfo = page.toPageInfo();
        final Pagination result = new Pagination();
        result.setPage(pageInfo.getPageNum());
        result.setSize(pageInfo.getPageSize());
        result.setPages(pageInfo.getPages());
        result.setTotal(pageInfo.getTotal());
        result.setFirstPage(pageInfo.isIsFirstPage());
        result.setLastPage(pageInfo.isIsLastPage());
        return result;
    }

}
