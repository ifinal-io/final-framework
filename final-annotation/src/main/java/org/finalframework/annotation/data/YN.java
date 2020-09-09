

package org.finalframework.annotation.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.finalframework.annotation.IEntity;
import org.finalframework.annotation.IEnum;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 是否有效枚举，用于标记记录是否有效。
 *
 * @author likly
 * @version 1.0
 * @date 2018-09-27 22:32
 * @see IEntity
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
     * Note: Don't use more, it would be remove in future.
     */
    @Deprecated
    DELETED(-1, "删除");
    /**
     * 枚举码
     */
    private final Integer code;
    private static final Map<Integer, YN> cache = Arrays.stream(values()).collect(Collectors.toMap(YN::getCode, Function.identity()));

    private final String desc;

    YN(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    @JsonCreator
    @SuppressWarnings("unused")
    public static YN valueOf(Integer value) {
        return cache.get(value);
    }

    @Override
    @JsonValue
    public Integer getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }


}
