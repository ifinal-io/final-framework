package com.ilikly.finalframework.data.entity.enums;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 22:32
 * @since 1.0
 */
public enum YN implements EnumEntity<Integer> {
    YES(1),
    NO(0);
    private final Integer code;

    YN(Integer code) {
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
