package org.finalframework.data.query;

import org.finalframework.core.Streamable;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-22 16:00:05
 * @since 1.0
 */
public interface Group extends Streamable<QProperty<?>>, Iterable<QProperty<?>> {

    static Group by(QProperty<?>... properties) {
        return by(Arrays.asList(properties));
    }

    static Group by(Collection<QProperty<?>> properties) {
        return GroupImpl.by(properties);
    }

    Group and(Group group);
}
