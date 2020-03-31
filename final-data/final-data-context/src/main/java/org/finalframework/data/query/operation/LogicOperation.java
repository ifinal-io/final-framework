package org.finalframework.data.query.operation;

/**
 * 逻辑运算符
 *
 * @author likly
 * @version 1.0
 * @date 2020-03-31 20:24:48
 * @since 1.0
 */
public interface LogicOperation extends Operation {
    Operation AND = new SimpleOperation("AND");
    Operation OR = new SimpleOperation("OR");
    Operation NOT = new SimpleOperation("NOT");
    Operation XOR = new SimpleOperation("XOR");
}
