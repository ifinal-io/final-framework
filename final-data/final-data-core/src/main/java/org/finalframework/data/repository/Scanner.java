package org.finalframework.data.repository;

import org.finalframework.core.Assert;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0
 * @date 2019-05-24 16:19:01
 * @see ScanListener
 * @since 1.0
 */
public interface Scanner<T> {

    /**
     * 返回当次扫描的结果
     *
     * @param index 扫描轮询次数，从1开始
     * @return 汝瓷扫描的结果
     */
    @Nullable
    T onScan(@NonNull Integer index);

    /**
     * 执行扫描
     *
     * @param listener 扫描结果监听
     */
    default void scan(@NonNull ScanListener<T> listener) {
        Assert.isNull(listener, "scan listener is null");
        listener.beforeScanning();
        int index = 1;
        do {
            final T data = onScan(index++);
            if (!listener.onScanning(data)) break;
            // 当扫描结果为空时，认为扫描已经完成，结束扫描
            if (Assert.isEmpty(data)) break;
        } while (true);
        listener.afterScanning();

    }

}
