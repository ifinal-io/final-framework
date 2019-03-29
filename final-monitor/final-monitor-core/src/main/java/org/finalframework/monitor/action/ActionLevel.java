package org.finalframework.monitor.action;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.finalframework.data.entity.enums.IEnum;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-29 17:15:11
 * @since 1.0
 */
public enum ActionLevel implements IEnum<Integer> {
    TRACE(1, "TRACE"),
    DEBUG(2, "DEBUG"),
    INFO(3, "INFO"),
    WARN(4, "WARN"),
    ERROR(5, "ERROR"),
    ;
    private final Integer code;
    private final String description;

    ActionLevel(Integer code, String description) {
        this.code = code;
        this.description = description;
    }


    @JsonCreator
    public static ActionLevel valueOf(Integer code) {
        for (ActionLevel item : values()) {
            if (item.code.equals(code)) return item;
        }

        return null;
    }


    @Override
    @JsonValue
    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
