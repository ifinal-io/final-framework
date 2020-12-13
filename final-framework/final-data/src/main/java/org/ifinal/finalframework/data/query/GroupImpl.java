package org.ifinal.finalframework.data.query;

import java.util.ArrayList;
import java.util.Collection;
import org.ifinal.finalframework.util.Asserts;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
final class GroupImpl extends ArrayList<QProperty<?>> implements Group {

    private GroupImpl(final Collection<QProperty<?>> properties) {

        this.addAll(properties);
    }

    public static Group by(final Collection<QProperty<?>> properties) {

        return new GroupImpl(properties);
    }

    @Override
    public Group and(final Group group) {

        Asserts.isNull(group, "Sort must not be null!");
        ArrayList<QProperty<?>> these = new ArrayList<>(this);

        for (QProperty<?> order : group) {
            these.add(order);
        }

        return GroupImpl.by(these);
    }

}

