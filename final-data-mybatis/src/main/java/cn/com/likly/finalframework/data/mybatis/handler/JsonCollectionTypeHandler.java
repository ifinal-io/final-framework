package cn.com.likly.finalframework.data.mybatis.handler;


import cn.com.likly.finalframework.data.json.Json;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-26 23:48
 * @since 1.0
 */
public abstract class JsonCollectionTypeHandler<E, T extends Collection<E>> extends CollectionTypeHandler<E, T> {

    public JsonCollectionTypeHandler(Class<E> type) {
        super(type);
    }

    @Override
    protected String setParameter(T parameter) {
        return Json.toJson(parameter);
    }
}
