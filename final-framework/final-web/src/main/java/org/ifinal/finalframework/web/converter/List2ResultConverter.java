package org.ifinal.finalframework.web.converter;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.ifinal.finalframework.core.annotation.result.Pagination;
import org.ifinal.finalframework.core.annotation.result.R;
import org.ifinal.finalframework.core.annotation.result.Result;
import org.ifinal.finalframework.util.function.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class List2ResultConverter<T> implements Converter<List<T>, Result<ArrayList<T>>> {

    public static final Logger logger = LoggerFactory.getLogger(List2ResultConverter.class);

    @Override
    public Result<ArrayList<T>> convert(List<T> source) {

        List<T> list = source instanceof Page ? ((Page<T>) source).getResult() : source;

        final Result<ArrayList<T>> result = R.success(new ArrayList<>(list));

        if (source instanceof Page) {
            result.setPagination(buildPageInfo((Page<T>) source));
        }
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
