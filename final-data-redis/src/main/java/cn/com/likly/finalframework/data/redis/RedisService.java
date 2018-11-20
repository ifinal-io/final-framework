package cn.com.likly.finalframework.data.redis;

import lombok.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-02 22:23
 * @since 1.0
 */
public interface RedisService {

    boolean set(@NonNull String key,@NonNull Object value);

}
