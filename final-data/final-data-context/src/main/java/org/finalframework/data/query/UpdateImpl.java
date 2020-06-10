package org.finalframework.data.query;

import lombok.NonNull;
import org.finalframework.core.Assert;
import org.finalframework.data.query.enums.UpdateOperation;

import java.util.*;
import java.util.stream.Stream;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-25 10:38
 * @since 1.0
 */
@SuppressWarnings("unused")
class UpdateImpl implements Update {
    private final List<UpdateSetOperation> modifierOps;

    private UpdateImpl() {
        this.modifierOps = new ArrayList<>(16);
    }

    private UpdateImpl(Collection<UpdateSetOperation> updateSets) {
        this.modifierOps = Assert.isEmpty(updateSets) ? new ArrayList<>() : new ArrayList<>(updateSets);

    }

    public static UpdateImpl update() {
        return new UpdateImpl();
    }

    public static UpdateImpl update(UpdateSetOperation... updateSets) {
        return new UpdateImpl(Arrays.asList(updateSets));
    }

    public static UpdateImpl update(Collection<UpdateSetOperation> updateSets) {
        return new UpdateImpl(updateSets);
    }


    public UpdateImpl set(@NonNull QProperty<?> property, @NonNull Object value) {
        modifierOps.add(new SimpleUpdateSetOperation(property, UpdateOperation.EQUAL, value));
        return this;
    }

    public UpdateImpl inc(@NonNull QProperty<?> property) {
        modifierOps.add(new SimpleUpdateSetOperation(property, UpdateOperation.INC, 1));
        return this;
    }

    public UpdateImpl incr(@NonNull QProperty<?> property, @NonNull Number value) {
        modifierOps.add(new SimpleUpdateSetOperation(property, UpdateOperation.INCR, value));
        return this;
    }

    public UpdateImpl dec(@NonNull QProperty<?> property) {
        modifierOps.add(new SimpleUpdateSetOperation(property, UpdateOperation.DEC, 1));
        return this;
    }

    public UpdateImpl decr(@NonNull QProperty<?> property, @NonNull Number value) {
        modifierOps.add(new SimpleUpdateSetOperation(property, UpdateOperation.DECR, value));
        return this;
    }

    @Override
    public Stream<UpdateSetOperation> stream() {
        return modifierOps.stream();
    }

    @Override
    public Iterator<UpdateSetOperation> iterator() {
        return modifierOps.iterator();
    }


}
