package org.finalframework.data.query.criterion.function;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.finalframework.data.annotation.IEnum;
import org.finalframework.data.annotation.Transient;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-20 21:08:37
 * @since 1.0
 */
@Transient
public enum FunctionOperator implements IEnum<String> {

    DATE("DATE", "DATE()", ""),

    MIN("MIN", "DATE()", ""),
    MAX("MAX", "DATE()", ""),
    SUM("SUM", "DATE()", ""),
    AVG("AVG", "DATE()", ""),

    AND("AND", "DATE()", ""),
    OR("OR", "DATE()", ""),
    NOT("NOT", "DATE()", ""),
    XOR("XOR", "DATE()", ""),

    JSON_EXTRACT("JSON_EXTRACT", "JSON_EXTRACT()", ""),
    JSON_CONTAINS("JSON_CONTAINS", "JSON_CONTAINS()", ""),
    JSON_UNQUOTE("JSON_UNQUOTE", "JSON_UNQUOTE()", ""),
    JSON_KEYS("JSON_KEYS", "JSON_KEYS()", ""),
    JSON_DEPTH("JSON_DEPTH", "JSON_DEPTH()", ""),
    JSON_LENGTH("JSON_LENGTH", "JSON_LENGTH()", ""),

    CONCAT("CONCAT", "CONCAT()", "");
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
    public String getDesc() {
        return description;
    }
}
