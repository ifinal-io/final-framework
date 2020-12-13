package org.ifinal.finalframework.data.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.ifinal.finalframework.data.query.enums.UpdateOperation;
import org.ifinal.finalframework.util.Asserts;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
class UpdateImpl extends ArrayList<UpdateSetOperation> implements Update {

    private UpdateImpl() {
        super(16);
    }

    private UpdateImpl(final Collection<UpdateSetOperation> updateSets) {

        if (Asserts.nonEmpty(updateSets)) {
            this.addAll(updateSets);
        }
    }

    public static UpdateImpl update() {
        return new UpdateImpl();
    }

    public static UpdateImpl update(final UpdateSetOperation... updateSets) {

        return new UpdateImpl(Arrays.asList(updateSets));
    }

    public static UpdateImpl update(final Collection<UpdateSetOperation> updateSets) {

        return new UpdateImpl(updateSets);
    }

    public UpdateImpl set(final @NonNull QProperty<?> property, final @NonNull Object value) {

        this.add(new SimpleUpdateSetOperation(property, UpdateOperation.EQUAL, value));
        return this;
    }

    public UpdateImpl inc(final @NonNull QProperty<?> property) {

        this.add(new SimpleUpdateSetOperation(property, UpdateOperation.INC, 1));
        return this;
    }

    public UpdateImpl incr(final @NonNull QProperty<?> property, final @NonNull Number value) {

        this.add(new SimpleUpdateSetOperation(property, UpdateOperation.INCR, value));
        return this;
    }

    public UpdateImpl dec(final @NonNull QProperty<?> property) {

        this.add(new SimpleUpdateSetOperation(property, UpdateOperation.DEC, 1));
        return this;
    }

    public UpdateImpl decr(final @NonNull QProperty<?> property, final @NonNull Number value) {

        this.add(new SimpleUpdateSetOperation(property, UpdateOperation.DECR, value));
        return this;
    }

}
