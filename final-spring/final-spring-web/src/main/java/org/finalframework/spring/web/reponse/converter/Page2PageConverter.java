package org.finalframework.spring.web.reponse.converter;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.finalframework.core.converter.Converter;

/**
 * @author likly
 * @version 1.0
 * @date 2019-09-24 23:52:24
 * @since 1.0
 */
public class Page2PageConverter implements Converter<Page, org.finalframework.data.result.Page> {
    @Override
    public org.finalframework.data.result.Page convert(Page source) {
        final PageInfo pageInfo = source.toPageInfo();
        org.finalframework.data.result.Page page = org.finalframework.data.result.Page.builder()
                .page(pageInfo.getPageNum())
                .size(pageInfo.getPageSize())
                .pages(pageInfo.getPages())
                .total(pageInfo.getTotal())
                .result(pageInfo.getList())
                .firstPage(pageInfo.isIsFirstPage())
                .lastPage(pageInfo.isIsLastPage())
                .build();
        return page;
    }
}
