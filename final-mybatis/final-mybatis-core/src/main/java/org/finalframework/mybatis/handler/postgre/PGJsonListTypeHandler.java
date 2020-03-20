package org.finalframework.mybatis.handler.postgre;


import java.util.List;
import org.finalframework.json.Json;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-26 23:55
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public class PGJsonListTypeHandler<T> extends PGJsonCollectionTypeHandler<T, List<T>> {
    public PGJsonListTypeHandler(Class<T> type) {
        super(type);
    }

    @Override
    protected List<T> getResult(String value, Class type) {
        return Json.toCollection(value, List.class, type);
    }
}
