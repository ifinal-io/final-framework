package org.finalframework.data.repository;

import org.finalframework.data.result.Page;

import java.io.Serializable;

/**
 * 扫描监听
 *
 * @author likly
 * @version 1.0
 * @date 2019-05-23 10:37:18
 * @since 1.0
 */
public interface ScanListener<T extends Serializable> {
    /**
     * 扫描开始
     */
    default void beforeScanning() {
    }

    boolean onScanning(Page<T> list);

    /**
     * 扫描结束
     */
    default void afterScanning() {
    }
}