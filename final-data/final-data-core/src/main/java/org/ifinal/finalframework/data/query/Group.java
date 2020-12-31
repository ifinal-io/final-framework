package org.ifinal.finalframework.data.query;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Group extends Iterable<QProperty<?>> {

    static Group by(QProperty<?>... properties) {
        return by(Arrays.asList(properties));
    }

    static Group by(Collection<QProperty<?>> properties) {
        return GroupImpl.by(properties);
    }

    Group and(Group group);

}
