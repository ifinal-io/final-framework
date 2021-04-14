package org.ifinal.finalframework.context.converter.result;

import org.ifinal.finalframework.core.annotation.result.Pagination;
import org.ifinal.finalframework.core.annotation.result.R;
import org.ifinal.finalframework.core.annotation.result.Result;
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
