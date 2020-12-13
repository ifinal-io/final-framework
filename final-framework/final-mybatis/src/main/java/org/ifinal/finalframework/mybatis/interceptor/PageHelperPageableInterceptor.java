package org.ifinal.finalframework.mybatis.interceptor;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.page.PageMethod;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.ifinal.finalframework.annotation.core.Pageable;
import org.ifinal.finalframework.util.Asserts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 分页拦截器
 *
 * <ul>
 * <li>单一参数实现了 {@link Pageable} 接口</li>
 * <li>参数列表中有一个参数实现了 {@link Pageable} 接口</li>
 * </ul>
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Intercepts(
    {
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
            RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
            RowBounds.class, ResultHandler.class, CacheKey.class,
            BoundSql.class}),
    }
)
@Order(Ordered.HIGHEST_PRECEDENCE + 100)
@Component
public class PageHelperPageableInterceptor extends PageableInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(PageHelperPageableInterceptor.class);

    @Override
    protected void startPage(final Pageable pageable) {

        if (Asserts.isNull(pageable) || Asserts.isNull(pageable.getPage()) || Asserts.isNull(pageable.getSize())) {
            return;
        }
        startPage(pageable.getPage(), pageable.getSize(), Boolean.TRUE.equals(pageable.getCount()), false, false);
    }

    /**
     * @param pageNum      页码
     * @param pageSize     每页显示数量
     * @param count        是否进行count查询
     * @param reasonable   分页合理化,null时用默认配置
     * @param pageSizeZero true且pageSize=0时返回全部结果，false时分页,null时用默认配置
     * @see PageHelper#startPage(int, int, boolean, Boolean, Boolean)
     */
    private void startPage(final int pageNum, final int pageSize, final boolean count, final Boolean reasonable,
        final Boolean pageSizeZero) {

        final Page<Object> result = PageMethod.startPage(pageNum, pageSize, count, reasonable, pageSizeZero);
        logger.info("pageResult:page={},size={},pages={},total={}",
            result.getPageNum(), result.getPageSize(), result.getPages(), result.getTotal());
    }

}
