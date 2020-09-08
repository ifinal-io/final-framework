/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.finalframework.data.query;

import lombok.NonNull;
import org.finalframework.core.Asserts;
import org.finalframework.data.query.enums.UpdateOperation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-25 10:38
 * @since 1.0
 */
@SuppressWarnings("unused")
class UpdateImpl extends ArrayList<UpdateSetOperation> implements Update {
    private UpdateImpl() {
        super(16);
    }

    private UpdateImpl(Collection<UpdateSetOperation> updateSets) {
        if (Asserts.nonEmpty(updateSets)) {
            this.addAll(updateSets);
        }
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
        this.add(new SimpleUpdateSetOperation(property, UpdateOperation.EQUAL, value));
        return this;
    }

    public UpdateImpl inc(@NonNull QProperty<?> property) {
        this.add(new SimpleUpdateSetOperation(property, UpdateOperation.INC, 1));
        return this;
    }

    public UpdateImpl incr(@NonNull QProperty<?> property, @NonNull Number value) {
        this.add(new SimpleUpdateSetOperation(property, UpdateOperation.INCR, value));
        return this;
    }

    public UpdateImpl dec(@NonNull QProperty<?> property) {
        this.add(new SimpleUpdateSetOperation(property, UpdateOperation.DEC, 1));
        return this;
    }

    public UpdateImpl decr(@NonNull QProperty<?> property, @NonNull Number value) {
        this.add(new SimpleUpdateSetOperation(property, UpdateOperation.DECR, value));
        return this;
    }


    @Override
    public Stream<UpdateSetOperation> stream() {
        return super.stream();
    }
}
