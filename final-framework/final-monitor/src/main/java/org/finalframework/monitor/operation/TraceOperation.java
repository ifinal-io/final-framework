package org.finalframework.monitor.operation;

import org.finalframework.aop.Executor;
import org.finalframework.aop.Operation;
import org.finalframework.aop.OperationHandler;

/**
 * @author likly
 * @version 1.0
 * @date 2019-07-09 16:49
 * @since 1.0
 */
public class TraceOperation implements Operation {

    private final String name;
    private final String trace;

    private final Class<? extends OperationHandler> handler;
    private final Class<? extends Executor> executor;

    private TraceOperation(Builder builder) {
        this.name = builder.name;
        this.trace = builder.trace;
        this.handler = builder.handler;
        this.executor = builder.executor;
    }

    public static Builder builder() {
        return new Builder();
    }


    @Override
    public String name() {
        return name;
    }

    public String trace() {
        return trace;
    }

    @Override
    public Class<? extends OperationHandler> handler() {
        return handler;
    }

    @Override
    public Class<? extends Executor> executor() {
        return executor;
    }

    public static class Builder implements org.finalframework.util.Builder<TraceOperation> {
        private String name;
        private String trace;
        private Class<? extends OperationHandler> handler;
        private Class<? extends Executor> executor;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder trace(String trace) {
            this.trace = trace;
            return this;
        }

        public Builder handler(Class<? extends OperationHandler> handler) {
            this.handler = handler;
            return this;
        }

        public Builder executor(Class<? extends Executor> executor) {
            this.executor = executor;
            return this;
        }


        @Override
        public TraceOperation build() {
            return new TraceOperation(this);
        }
    }
}
