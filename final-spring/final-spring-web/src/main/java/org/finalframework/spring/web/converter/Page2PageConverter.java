package org.finalframework.spring.web.converter;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.finalframework.core.converter.Converter;

import java.util.ArrayList;

/**
 * 将 {@link Page}对象转化成 {@link org.finalframework.data.result.Page}对象的转换器
 *
 * @author likly
 * @version 1.0
 * @date 2019-09-24 23:52:24
 * @since 1.0
 */
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
