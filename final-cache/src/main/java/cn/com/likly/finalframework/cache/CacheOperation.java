package cn.com.likly.finalframework.cache;

import java.util.concurrent.TimeUnit;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-01 09:53
 * @since 1.0
 */
public interface CacheOperation {

    OperationType getOperation();

    String getKey();

    String getField();

    String getCondition();

    String getExpired();

    TimeUnit getTimeUnit();

    enum OperationType {
        SET,
        DEL,
        HSET,
        HDEL,
    }

    interface Builder {
        Builder setOperation(OperationType operation);

        Builder setKey(String key);

        Builder setField(String field);

        Builder setCondition(String condition);

        Builder setExpired(String expired);

        Builder setTimeUnit(TimeUnit timeUnit);

        CacheOperation build();
    }

}
