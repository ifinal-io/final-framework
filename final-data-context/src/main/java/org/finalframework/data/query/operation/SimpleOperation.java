

package org.finalframework.data.query.operation;


/**
 * @author likly
 * @version 1.0
 * @date 2020-03-31 20:25:22
 * @since 1.0
 */
public class SimpleOperation implements Operation {
    private final String operation;

    public SimpleOperation(String operation) {
        this.operation = operation;
    }

    @Override
    public String name() {
        return this.operation;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SimpleOperation) {
            return operation.equals(((SimpleOperation) obj).name());
        }
        return false;
    }
}

