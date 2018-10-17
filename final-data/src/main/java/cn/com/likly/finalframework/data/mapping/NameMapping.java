package cn.com.likly.finalframework.data.mapping;

import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-15 18:25
 * @since 1.0
 */
public interface NameMapping {
    @NonNull
    String map(@NonNull String name);
}
