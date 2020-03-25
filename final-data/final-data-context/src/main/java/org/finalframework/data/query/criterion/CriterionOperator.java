package org.finalframework.data.query.criterion;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.finalframework.data.annotation.IEnum;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-20 20:03:54
 * @since 1.0
 */
public enum CriterionOperator implements IEnum<String> {

    NULL("null", "IS NULL", "空"),
    NOT_NULL("nonnull", "IS NOT NULL", "非空"),

    EQUAL("eq", "=", "等于"),
    NOT_EQUAL("neq", "!=", "不等于"),
    GREAT_THAN("gt", ">", "大于"),
    GREAT_THAN_EQUAL("gte", ">=", "大于等于"),
    LESS_THAN("lt", "<", "小于"),
    LESS_THAN_EQUAL("lte", "<=", "小于等于"),

    LIKE("like", "<=", "类似"),
    NOT_LIKE("notLike", "<=", "不类似"),

    BETWEEN("between", "BETWEEN", "在...之间"),
    NOT_BETWEEN("notBetween", "NOT BETWEEN", "在不...之间"),

    IN("in", "IN", "在...之间"),
    NOT_IN("nin", "NOT IN", "在不...之间"),

    ASSERT_TRUE("assertTrue", "ASSERT", "断言"),
    ASSERT_FALSE("assertFalse", "ASSERT", "断言"),
    ;

    /**
     * 枚举码
     */
    private final String code;
    private final String operator;
    private static final Map<String, CriterionOperator> cache = Arrays.stream(values())
            .collect(Collectors.toMap(CriterionOperator::getCode, Function.identity()));

    private final String desc;

    CriterionOperator(String code, String operator, String desc) {
        this.code = code;
        this.operator = operator;
        this.desc = desc;
    }


    @JsonCreator
    public static CriterionOperator of(String value) {
        return cache.get(value);
    }

    @Override
    @JsonValue
    public String getCode() {
        return code;
    }

    public String getOperator() {
        return operator;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
