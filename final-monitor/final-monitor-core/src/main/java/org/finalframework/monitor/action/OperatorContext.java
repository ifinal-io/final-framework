package org.finalframework.monitor.action;


/**
 * 操作者上下文，用于记录当前的操作用户，可以是后台管理人员，也可以是用户。
 *
 * @author likly
 * @version 1.0
 * @date 2019-03-28 10:58:42
 * @since 1.0
 */
public final class OperatorContext<T> {

    private static final OperatorContext instance = new OperatorContext();
    private final ThreadLocal<T> operator = new ThreadLocal<>();

    private OperatorContext() {
    }

    @SuppressWarnings("unchecked")
    public static <T> OperatorContext<T> getInstance() {
        return instance;
    }

    public void set(T operator) {
        this.operator.set(operator);
    }

    public T get() {
        return operator.get();
    }

    public void clear() {
        operator.remove();
    }


}
