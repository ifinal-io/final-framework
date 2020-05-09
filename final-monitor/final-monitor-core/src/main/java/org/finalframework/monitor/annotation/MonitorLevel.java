package org.finalframework.monitor.annotation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.finalframework.data.annotation.IEnum;
import org.finalframework.data.annotation.Transient;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 监控级别
 *
 * @author likly
 * @version 1.0
 * @date 2019-03-29 17:15:11
 * @since 1.0
 */
@Transient
public enum MonitorLevel implements IEnum<Integer> {
    TRACE(1, "TRACE"),
    DEBUG(2, "DEBUG"),
    INFO(3, "INFO"),
    WARN(4, "WARN"),
    ERROR(5, "ERROR"),
    ;

    private static final Map<Integer, MonitorLevel> cache = Arrays.stream(MonitorLevel.values()).collect(Collectors.toMap(MonitorLevel::getCode, Function.identity()));

    private final Integer code;
    private final String description;

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

    @Override
    @SuppressWarnings("unused")
    public String getDesc() {
        return description;
    }
}
