package org.finalframework.data.query;


import org.finalframework.core.Asserts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-22 16:01:19
 * @since 1.0
 */
final class GroupImpl extends ArrayList<QProperty<?>> implements Group {

    private GroupImpl(Collection<QProperty<?>> properties) {
        this.addAll(properties);
    }

    public static Group by(Collection<QProperty<?>> properties) {
        return new GroupImpl(properties);
    }


    @Override
    public Group and(Group group) {
        Asserts.isNull(group, "Sort must not be null!");
        ArrayList<QProperty<?>> these = new ArrayList<>(this);

        for (QProperty<?> order : group) {
            these.add(order);
        }

        return GroupImpl.by(these);
    }

    @Override
    public Stream<QProperty<?>> stream() {
        return super.stream();
    }
}

