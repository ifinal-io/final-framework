package cn.com.likly.finalframework.mybatis.handler;


import cn.com.likly.finalframework.json.Json;

import java.util.Set;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-26 23:55
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public class JsonSetTypeHandler<T> extends JsonCollectionTypeHandler<T, Set<T>> {


    public JsonSetTypeHandler(Class<T> type) {
        super(type);
    }

    @Override
    protected Set<T> getResult(String value, Class type) {
        return Json.parse(value, Set.class, type);
    }
}
