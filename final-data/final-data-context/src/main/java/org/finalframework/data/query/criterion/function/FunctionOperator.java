package org.finalframework.data.query.criterion.function;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.finalframework.data.annotation.IEnum;

/**
 * @author sli
 * @version 1.0
 * @date 2020-03-20 21:08:37
 * @since 1.0
 */
public enum FunctionOperator implements IEnum<String> {

    DATE("date", "DATE()", ""),

    MIN("date", "DATE()", ""),
    MAX("date", "DATE()", ""),
    SUM("date", "DATE()", ""),
    AVG("date", "DATE()", ""),

    AND("date", "DATE()", ""),
    OR("date", "DATE()", ""),
    NOT("date", "DATE()", ""),
    XOR("date", "DATE()", ""),

    JSON_EXTRACT("date", "JSON_EXTRACT()", ""),
    JSON_CONTAINS("date", "JSON_CONTAINS()", ""),
    JSON_UNQUOTE("date", "JSON_UNQUOTE()", ""),

    CONCAT("concat", "CONCAT()", "");
    /**
     * 枚举码
     */
    private final String code;
    private final String operator;
    private static final Map<String, FunctionOperator> cache = Arrays.stream(values())
        .collect(Collectors.toMap(FunctionOperator::getCode, Function
            .identity()));

    private final String description;

    FunctionOperator(String code, String operator, String description) {
        this.code = code;
        this.operator = operator;
        this.description = description;
    }

    @Override
    public String getCode() {
        return code;
    }

    public String getOperator() {
        return operator;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
