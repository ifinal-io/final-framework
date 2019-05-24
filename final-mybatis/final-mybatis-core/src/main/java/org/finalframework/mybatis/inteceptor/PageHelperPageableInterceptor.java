package org.finalframework.mybatis.inteceptor;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.finalframework.core.Assert;
import org.finalframework.data.query.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 分页拦截器
 *
 * <ul>
 * <li>单一参数实现了 {@link Pageable} 接口</li>
 * <li>参数列表中有一个参数实现了 {@link Pageable} 接口</li>
 * </ul>
 *
 * @author likly
 * @version 1.0
 * @date 2019-01-15 21:58:13
 * @since 1.0
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Intercepts(
        {
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        }
)
public class PageHelperPageableInterceptor extends PageableInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(PageHelperPageableInterceptor.class);

    @Override
    protected void startPage(Pageable pageable) {
        if (Assert.isNull(pageable) || Assert.isNull(pageable.getPage()) || Assert.isNull(pageable.getSize())) return;
        final Page<Object> result = PageHelper.startPage(pageable.getPage(), pageable.getSize(), Boolean.TRUE.equals(pageable.getCount()), pageable.getReasonable(), pageable.getPageSizeZero());
        logger.info("pageResult:page={},size={},pages={},total={}",
                result.getPageNum(), result.getPageSize(), result.getPages(), result.getTotal());

    }

}
