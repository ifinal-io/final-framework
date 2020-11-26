package org.ifinal.finalframework.data.query;

import org.ifinal.finalframework.util.stream.Streamable;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
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
