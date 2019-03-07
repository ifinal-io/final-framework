package com.ilikly.finalframework.cache.interceptor;


import com.ilikly.finalframework.cache.Cache;
import org.springframework.lang.NonNull;

import java.io.Serializable;

/**
 * 缓存锁上下文
 *
 * @author likly
 * @version 1.0
 * @date 2019-03-06 22:53:10
 * @see com.ilikly.finalframework.cache.annotation.CacheLock
 * @see CacheLockOperation
 * @see CacheLockOperationInvocation
 * @since 1.0
 */
public class CacheLockContext implements Serializable {

    private static final long serialVersionUID = -246436378495998184L;
    /**
     * 锁标定,如果获取锁成功，其值为 {@linkplain true},否则为 {@linkplain false}。
     */
    private final boolean lock;
    /**
     * 锁Key
     */
    @NonNull
    private final Object key;
    /**
     * 锁value
     */
    @NonNull
    private final Object value;
    /**
     * 缓存器
     */
    @NonNull
    private final Cache cache;

    public CacheLockContext(boolean lock, Object key, Object value, Cache cache) {
        this.lock = lock;
        this.key = key;
        this.value = value;
        this.cache = cache;
    }

    public boolean isLock() {
        return lock;
    }

    @NonNull
    public Object getKey() {
        return key;
    }

    @NonNull
    public Object getValue() {
        return value;
    }

    public boolean unlock() {
        return cache.unlock(key, value);
    }


}
