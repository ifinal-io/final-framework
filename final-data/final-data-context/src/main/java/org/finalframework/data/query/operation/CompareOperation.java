package org.finalframework.data.query.operation;

/**
 * 比较运算符
 *
 * @author likly
 * @version 1.0
 * @date 2020-03-31 20:24:48
 * @since 1.0
 */
public interface CompareOperation extends Operation {
    Operation NULL = new SimpleOperation("NULL");
    Operation NOT_NULL = new SimpleOperation("NOT_NULL");
    Operation EQUAL = new SimpleOperation("EQUAL");
    Operation NOT_EQUAL = new SimpleOperation("NOT_EQUAL");
    Operation GREAT_THAN = new SimpleOperation("GREAT_THAN");
    Operation GREAT_THAN_EQUAL = new SimpleOperation("GREAT_THAN_EQUAL");
    Operation LESS_THAN = new SimpleOperation("LESS_THAN");
    Operation LESS_THAN_EQUAL = new SimpleOperation("LESS_THAN_EQUAL");
    Operation IN = new SimpleOperation("IN");
    Operation NOT_IN = new SimpleOperation("NOT_IN");
    Operation LIKE = new SimpleOperation("LIKE");
    Operation NOT_LIKE = new SimpleOperation("NOT_LIKE");
    Operation BETWEEN = new SimpleOperation("BETWEEN");
    Operation NOT_BETWEEN = new SimpleOperation("NOT_BETWEEN");
}
