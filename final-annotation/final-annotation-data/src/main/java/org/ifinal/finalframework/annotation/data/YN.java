package org.ifinal.finalframework.annotation.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.ifinal.finalframework.annotation.core.IEntity;
import org.ifinal.finalframework.annotation.core.IEnum;
import org.springframework.context.annotation.Description;

/**
 * 是否有效枚举，用于标记记录是否有效。
 *
 * @author likly
 * @version 1.0.0
 * @see IEntity
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
@Description("有效标记")
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

    private final String desc;

}
