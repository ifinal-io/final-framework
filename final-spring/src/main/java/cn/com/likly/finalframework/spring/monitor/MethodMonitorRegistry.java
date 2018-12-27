package cn.com.likly.finalframework.spring.monitor;

import cn.com.likly.finalframework.core.Streable;
import lombok.NonNull;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-26 13:26:33
 * @since 1.0
 */
public final class MethodMonitorRegistry implements Streable<MethodMonitorListener> {
    private static final MethodMonitorRegistry INSTANCE = new MethodMonitorRegistry();
    private final List<MethodMonitorListener> methodMonitorListeners = new CopyOnWriteArrayList<>();

    public static MethodMonitorRegistry getInstance() {
        return INSTANCE;
    }

    public void register(@NonNull MethodMonitorListener listener) {
        this.methodMonitorListeners.add(listener);
    }

    public void unRegister(@NonNull MethodMonitorListener listener) {
        this.methodMonitorListeners.remove(listener);
    }

    @Override
    public Stream<MethodMonitorListener> stream() {
        return methodMonitorListeners.stream();
    }
}
