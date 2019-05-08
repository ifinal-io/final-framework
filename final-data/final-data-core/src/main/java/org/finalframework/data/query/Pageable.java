package org.finalframework.data.query;

import org.springframework.lang.Nullable;

/**
 * 可分页的
 *
 * @author likly
 * @version 1.0
 * @date 2019-02-18 19:21:21
 * @since 1.0
 */
public interface Pageable {
    /**
     * 返回分页页码
     *
     * @return 分页页码
     */
    @Nullable
    Integer getPage();

    /**
     * 返回页面容量
     *
     * @return 页面容量
     */
    @Nullable
    Integer getSize();
}
