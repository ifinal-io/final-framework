package org.finalframework.data.query.operation;


/**
 * @author likly
 * @version 1.0
 * @date 2020-03-31 20:29:21
 * @since 1.0
 */
public interface MathOperation extends Operation {
    Operation MIN = new SimpleOperation("MIN");
    Operation MAX = new SimpleOperation("MAX");
    Operation SUM = new SimpleOperation("SUM");
    Operation AVG = new SimpleOperation("AVG");
}

