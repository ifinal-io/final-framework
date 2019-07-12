package org.finalframework.monitor.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.finalframework.data.entity.enums.IEnum;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 监控级别
 * @author likly
 * @version 1.0
 * @date 2019-03-29 17:15:11
 * @since 1.0
 */
public enum MonitorLevel implements IEnum<Integer> {
    TRACE(1, "TRACE"),
    DEBUG(2, "DEBUG"),
    INFO(3, "INFO"),
    WARN(4, "WARN"),
    ERROR(5, "ERROR"),
    ;

    private static final Map<Integer, MonitorLevel> cache;

    private final Integer code;
    private final String description;

    static {
        cache = new HashMap<>(MonitorLevel.values().length);
        Arrays.stream(MonitorLevel.values()).forEach(item -> cache.put(item.code, item));
    }


    MonitorLevel(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    @JsonCreator
    @SuppressWarnings("unused")
    public static MonitorLevel valueOf(Integer code) {
        return cache.get(code);
    }


    @Override
    @JsonValue
    public Integer getCode() {
        return code;
    }

    @SuppressWarnings("unused")
    public String getDescription() {
        return description;
    }
}
