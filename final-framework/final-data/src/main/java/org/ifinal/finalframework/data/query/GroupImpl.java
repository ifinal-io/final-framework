package org.ifinal.finalframework.data.query;

import org.springframework.lang.NonNull;

import org.ifinal.finalframework.util.Asserts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

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

        Asserts.requiredNonNull(group, "Sort must not be null!");
        ArrayList<QProperty<?>> these = new ArrayList<>(this);

        for (QProperty<?> order : group) {
            these.add(order);
        }

        return GroupImpl.by(these);
    }

    @Override
    public void apply(@NonNull final StringBuilder sql, @NonNull final String expression) {
        sql.append("GROUP BY ")
            .append( this.stream().map(QProperty::getColumn).collect(Collectors.joining(",")));

    }

}

