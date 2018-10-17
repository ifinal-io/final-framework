package cn.com.likly.finalframework.mybatis.handler;


import cn.com.likly.finalframework.data.json.Json;

import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-26 23:55
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public class JsonListTypeHandler<T> extends JsonCollectionTypeHandler<T, List<T>> {
    public JsonListTypeHandler(Class<T> type) {
        super(type);
    }

    @Override
    protected List<T> getResult(String value, Class type) {
        return Json.parse(value, List.class, type);
    }
}
