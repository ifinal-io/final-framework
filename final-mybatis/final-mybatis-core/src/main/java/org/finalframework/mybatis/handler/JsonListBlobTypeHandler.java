package org.finalframework.mybatis.handler;

import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-20 11:20:53
 * @since 1.0
 */
@Deprecated
public class JsonListBlobTypeHandler<E> extends StringBlobTypeHandler<List<E>> {
    private final Class<E> type;
    public JsonListBlobTypeHandler(Class<E> type) {
        this.type = type;
    }

    @Override
    protected List<E> getNullableResult(String string) {
        return MybatisJson.toList(string, type);
    }

}
