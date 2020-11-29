package org.ifinal.finalframework.data.query.builder;

import org.ifinal.finalframework.data.query.QProperty;
import org.ifinal.finalframework.util.Builder;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface SqlBuilder<T> extends Builder<String> {

    default String formatProperty(QProperty property) {
        return property.getColumn();
    }


}
