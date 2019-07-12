package org.finalframework.monitor;


/**
 * 操作者上下文，用于记录当前的操作用户，可以是后台管理人员，也可以是用户。
 *
 * @author likly
 * @version 1.0
 * @date 2019-03-28 10:58:42
 * @since 1.0
 */
public final class OperatorContext {

    private static final ThreadLocal<Object> operator = new ThreadLocal<>();

    private OperatorContext() {
    }

    @SuppressWarnings("unchecked")
    public static <T> T get() {
        return (T) OperatorContext.operator.get();
    }

    public static <T> void set(T operator) {
        OperatorContext.operator.set(operator);
    }

    public static void clear() {
        OperatorContext.operator.remove();
    }


}
