package org.finalframework.data.query.criterion.operator;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 20:47:56
 * @see DefaultFunctionOperator
 * @since 1.0
 */
public interface FunctionOperator {

    /**
     * DATE()
     */
    String DATE = "DATE";
    /**
     * MIN()
     */
    String MIN = "MIN";
    /**
     * MAX()
     */
    String MAX = "MAX";
    /**
     * SUM()
     */
    String SUM = "SUM";
    /**
     * AVG()
     */
    String AVG = "AVG";
    /**
     * a & b
     */
    String AND = "AND";
    /**
     * a | b
     */
    String OR = "OR";
    /**
     * ~a
     */
    String NOT = "NOT";
    /**
     * a ^ b
     */
    String XOR = "XOR";
    /**
     * JSON_EXTRACT(json, path)
     */
    String JSON_EXTRACT = "JSON_EXTRACT";


    String name();
}
