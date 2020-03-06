package org.finalframework.data.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 是否有效枚举，用于标记记录是否有效。
 *
 * @author likly
 * @version 1.0
 * @date 2018-09-27 22:32
 * @see org.finalframework.data.entity.IEntity
 * @since 1.0
 */
public enum YN implements IEnum<Integer> {
    /**
     * 有效
     */
    YES(1, "有效"),
    /**
     * 无效
     */
    NO(0, "无效"),
    /**
     * 删除
     */
    DELETED(-1, "删除");
    /**
     * 枚举码
     */
    private final Integer code;
    private static final Map<Integer, YN> cache = Arrays.stream(values()).collect(Collectors.toMap(YN::getCode, Function.identity()));

    private final String description;

    YN(Integer code, String description) {
        this.code = code;
        this.description = description;
    }


    @JsonCreator
    public static YN valueOf(int value) {
        return cache.get(value);
    }

    @Override
    @JsonValue
    public Integer getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }


}
