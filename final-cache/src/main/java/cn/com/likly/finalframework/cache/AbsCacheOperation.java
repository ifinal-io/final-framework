package cn.com.likly.finalframework.cache;

import java.util.concurrent.TimeUnit;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 17:57:01
 * @since 1.0
 */
public interface AbsCacheOperation extends CacheOperation {

    String key();

    String field();

    String condition();

    String expired();

    long ttl();

    TimeUnit timeUnit();

    interface Builder<B extends Builder, O extends AbsCacheOperation> {

        B key(String key);

        B field(String field);

        B condition(String condition);

        B expired(String expired);

        B ttl(long ttl);

        B timeUnit(TimeUnit timeUnit);

        O build();
    }
}
