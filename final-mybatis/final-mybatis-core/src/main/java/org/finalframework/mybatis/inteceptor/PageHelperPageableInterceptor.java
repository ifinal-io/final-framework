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
@SuppressWarnings({"rawtypes"})
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
    private void startPage(int pageNum, int pageSize, boolean count, Boolean reasonable, Boolean pageSizeZero) {
        final Page<Object> result = PageHelper.startPage(pageNum, pageSize, count, reasonable, pageSizeZero);
        logger.info("pageResult:page={},size={},pages={},total={}",
                result.getPageNum(), result.getPageSize(), result.getPages(), result.getTotal());
    }

}
