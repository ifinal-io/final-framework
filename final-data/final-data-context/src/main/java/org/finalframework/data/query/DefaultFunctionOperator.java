package org.finalframework.data.query;


import org.finalframework.core.Assert;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 21:02:16
 * @since 1.0
 */
public class DefaultFunctionOperator implements FunctionOperator {

    /**
     * DATE()
     */
    public static final FunctionOperator DATE = new DefaultFunctionOperator("DATE");
    public static final FunctionOperator AVG = new DefaultFunctionOperator("AVG");
    public static final FunctionOperator MAX = new DefaultFunctionOperator("MAX");
    public static final FunctionOperator MIN = new DefaultFunctionOperator("MIN");
    public static final FunctionOperator SUM = new DefaultFunctionOperator("SUM");
    /**
     * a & b
     */
    public static final FunctionOperator AND = new DefaultFunctionOperator("AND");
    /**
     * a | b
     */
    public static final FunctionOperator OR = new DefaultFunctionOperator("OR");
    /**
     * a ^ b
     */
    public static final FunctionOperator XOR = new DefaultFunctionOperator("XOR");
    /**
     * ~ a
     */
    public static final FunctionOperator NOT = new DefaultFunctionOperator("NOT");

    private final String name;

    public DefaultFunctionOperator(@NonNull String name) {
        Assert.isBlank(name, "name is blank");
        this.name = name;
    }

    @Override
    @NonNull
    public String name() {
        return this.name;
    }


    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FunctionOperator) {
            return name.equals(((FunctionOperator) obj).name());
        }
        return false;
    }

    @Override
    public String toString() {
        return name;
    }
}
