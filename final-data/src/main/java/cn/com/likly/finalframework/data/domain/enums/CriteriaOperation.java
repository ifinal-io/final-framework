package cn.com.likly.finalframework.data.domain.enums;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-12 16:37
 * @since 1.0
 */
public enum CriteriaOperation {
    EQUAL, NOT_EQUAL,
    GREATER_THAN, GREATER_EQUAL_THAN,
    LESS_THAN, LESS_EQUAL_THAN,
    IN, NOT_IN,
    START_WITH, NOT_START_WITH,
    END_WITH, NOT_END_WITH,
    CONTAINS, NOT_CONTAINS,
    LIKE, NOT_LIKE,
    BEFORE, AFTER,
    DATE_BEFORE, DATE_AFTER,
    BETWEEN, NOT_BETWEEN,
    DATE_BETWEEN, NOT_DATE_BETWEEN,
    NULL, NOT_NULL
}
