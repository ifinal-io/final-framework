package org.finalframework.spring.web.converter;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.finalframework.core.converter.Converter;

/**
 * @author likly
 * @version 1.0
 * @date 2019-05-22 14:55:56
 * @since 1.0
 */
public class Page2PageResultConverter implements Converter<Page, org.finalframework.data.result.Page> {

    public static final Page2PageResultConverter INSTANCE = new Page2PageResultConverter();

    /**
     * @param null
     * @return
     * @author likly
     * @date 2019-05-23 15:52
     * @since 1.0
     */
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
