package org.finalframework.data.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 22:32
 * @since 1.0
 */
public enum YN implements IEnum<Integer> {
    /**
     * 有效
     */
    YES(1),
    /**
     * 无效
     */
    NO(0);
    /**
     * 枚举码
     */
    private final Integer code;


    YN(Integer code) {
        this.code = code;
    }

    @JsonCreator
    public static YN valueOf(int value) {
        for (YN yn : values()) {
            if (yn.code.equals(value)) {
                return yn;
            }
        }
        return null;
    }

    @Override
    @JsonValue
    public Integer getCode() {
        return code;
    }
}
