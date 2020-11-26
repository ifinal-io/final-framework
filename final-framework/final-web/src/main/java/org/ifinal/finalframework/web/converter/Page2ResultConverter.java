package org.ifinal.finalframework.web.converter;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.ifinal.finalframework.annotation.result.Pagination;
import org.ifinal.finalframework.annotation.result.R;
import org.ifinal.finalframework.annotation.result.Result;
import org.ifinal.finalframework.util.function.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
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
