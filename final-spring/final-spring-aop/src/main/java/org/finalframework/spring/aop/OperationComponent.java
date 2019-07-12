package org.finalframework.spring.aop;

import java.lang.annotation.Annotation;

/**
 * Operation 组件
 *
 * @author likly
 * @version 1.0
 * @date 2019-03-27 21:50:40
 * @since 1.0
 */
public class OperationComponent implements Comparable<OperationComponent> {
    private final Class<? extends Annotation> annotation;
    private final OperationAnnotationBuilder builder;
    private final OperationHandler handler;
    private final Invocation invocation;
    private final Integer order;

    private OperationComponent(Builder builder) {
        try {
            this.annotation = builder.annotation;
            this.builder = builder.builder.newInstance();
            this.handler = builder.handler.newInstance();
            this.invocation = builder.invocation.newInstance();
            this.order = builder.order;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }

    }


    public static Builder builder() {
        return new Builder();
    }


    public Class<? extends Annotation> getAnnotation() {
        return annotation;
    }

    public OperationAnnotationBuilder getBuilder() {
        return builder;
    }

    public OperationHandler getHandler() {
        return handler;
    }

    public Invocation getInvocation() {
        return invocation;
    }

    public Integer getOrder() {
        return order;
    }

    @Override
    public String toString() {
        return annotation.getSimpleName() + "Component";
    }

    @Override
    public int compareTo(OperationComponent o) {
        return this.order.compareTo(o.order);
    }

    public static class Builder implements org.finalframework.core.Builder<OperationComponent> {
        private Class<? extends Annotation> annotation;
        private Class<? extends OperationAnnotationBuilder> builder;
        private Class<? extends OperationHandler> handler;
        private Class<? extends Invocation> invocation;
        private Integer order;

        public Builder annotation(Class<? extends Annotation> annotation) {
            this.annotation = annotation;
            return this;
        }

        public Builder builder(Class<? extends OperationAnnotationBuilder> builder) {
            this.builder = builder;
            return this;
        }

        public Builder handler(Class<? extends OperationHandler> handler) {
            this.handler = handler;
            return this;
        }

        public Builder invocation(Class<? extends Invocation> invocation) {
            this.invocation = invocation;
            return this;
        }

        public Builder order(Integer order) {
            this.order = order == null ? 0 : order;
            return this;
        }

        @Override
        public OperationComponent build() {
            return new OperationComponent(this);
        }
    }
}
