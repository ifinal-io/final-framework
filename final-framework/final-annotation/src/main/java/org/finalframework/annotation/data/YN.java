package org.finalframework.annotation.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.finalframework.annotation.IEntity;
import org.finalframework.annotation.IEnum;
import org.finalframework.auto.service.annotation.AutoService;
import org.springframework.context.annotation.Description;
import org.springframework.lang.NonNull;

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
 * @see IEntity
 * @since 1.0
 */
@Description("有效标记")
//@AutoService(IEnum.class)
public enum YN implements IEnum<Integer> {
    /**
     * 有效
     */
    YES(1, "有效"),
    /**
     * 无效
     */
    NO(0, "无效");
    /**
     * 枚举码
     */
    private final Integer code;
    private static final Map<Integer, YN> CACHE = Arrays.stream(values()).collect(Collectors.toMap(YN::getCode, Function.identity()));

    private final String desc;

    YN(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    @JsonCreator
    public static YN valueOf(Integer value) {
        return CACHE.get(value);
    }

    @Override
    @NonNull
//    @JsonValue
    public Integer getCode() {
        return code;
    }

    @Override
    @NonNull
    public String getDesc() {
        return desc;
    }


    @Override
    public String toString() {
        return getDesc();
    }
}
