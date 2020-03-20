package org.finalframework.data.query.function;

import org.springframework.lang.NonNull;

import javax.validation.constraints.NotNull;

/**
 * @author likly
 * @version 1.0
 * @date 2019-12-16 17:47:00
 * @since 1.0
 */
public interface JsonFunction<V, R> extends Function {
    /**
     * 获取 JSON 字符串中指定的属性值
     *
     * @param path 指定的属性路径，以$开关
     */
    R jsonExtract(@NonNull String path);

    default R jsonContains(@NotNull Object value) {
        return jsonContains(value, null);
    }

    R jsonContains(@NotNull Object value, String path);

    /**
     * 去掉查询结果中首尾的双引号
     */
    R jsonUnquote();
}
