package org.finalframework.data.repository;

import org.finalframework.data.result.Page;
import org.finalframework.data.result.PageInfo;

import java.io.Serializable;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2019-12-09 10:48:17
 * @since 1.0
 */
public interface PageScanListener<T extends Serializable> extends ScanListener<Page<T>> {

    @Override
    default boolean onScanning(Page<T> page) {
        return onScanning(page.toPageInfo(), page.getResult());
    }

    boolean onScanning(PageInfo page, List<T> data);
}
