package org.finalframework.data.repository;

/**
 * 扫描监听
 *
 * @author likly
 * @version 1.0
 * @date 2019-05-23 10:37:18
 * @see Scanner
 * @since 1.0
 */
public interface ScanListener<T> {
    /**
     * 扫描开始
     */
    default void beforeScanning() {
    }

    boolean onScanning(T data);

    /**
     * 扫描结束
     */
    default void afterScanning() {
    }
}
