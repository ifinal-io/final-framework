package org.ifinal.finalframework.data.query.criterion.function;

import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface JsonFunction<R> {
    /**
     * 获取 JSON 字符串中指定的属性值
     *
     * @param path 指定的属性路径，以$开关
     * @return result
     */
    R jsonExtract(@NonNull String path);

    R jsonKeys();

    R jsonLength();

    R jsonDepth();

    /**
     * 去掉查询结果中首尾的双引号
     *
     * @return result
     */
    R jsonUnquote();
}
