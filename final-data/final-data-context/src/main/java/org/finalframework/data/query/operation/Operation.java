package org.finalframework.data.query.operation;

import org.finalframework.data.query.operation.function.Function;
import org.finalframework.data.query.operation.function.AvgFunctionOperation;
import org.finalframework.data.query.operation.function.MaxFunctionOperation;
import org.finalframework.data.query.operation.function.MinFunctionOperation;
import org.finalframework.data.query.operation.function.SumFunctionOperation;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-31 20:13:24
 * @since 1.0
 */
public interface Operation {

    String name();

    /**
     * 比较运算符
     *
     * @author likly
     * @version 1.0
     * @date 2020-03-31 20:24:48
     * @since 1.0
     */
    enum CompareOperation implements Operation {
        NULL, NOT_NULL,
        EQUAL, NOT_EQUAL,
        GREAT_THAN, GREAT_THAN_EQUAL,
        LESS_THAN, LESS_THAN_EQUAL,
        IN, NOT_IN,
        LIKE, NOT_LIKE,
        BETWEEN, NOT_BETWEEN
    }

    /**
     * @author likly
     * @version 1.0
     * @date 2020-03-31 20:29:21
     * @since 1.0
     */
    enum MathOperation implements Operation {

        MIN, MAX, SUM, AVG;

        public static Function min() {
            return new MinFunctionOperation();
        }

        public static Function max() {
            return new MaxFunctionOperation();
        }

        public static Function sum() {
            return new SumFunctionOperation();
        }

        public static Function avg() {
            return new AvgFunctionOperation();
        }


    }
}
