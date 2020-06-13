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
import org.finalframework.core.Streamable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-25 10:38
 * @since 1.0
 */
@SuppressWarnings("unused")
public interface Update extends Streamable<UpdateSetOperation>, Iterable<UpdateSetOperation>, SqlNode {

    static Update update() {
        return UpdateImpl.update();
    }

    static Update update(UpdateSetOperation... updateSets) {
        return UpdateImpl.update(Arrays.asList(updateSets));
    }

    static Update update(Collection<UpdateSetOperation> updateSets) {
        return UpdateImpl.update(updateSets);
    }

    Update set(@NonNull QProperty<?> property, @NonNull Object value);

    Update inc(@NonNull QProperty<?> property);

    Update incr(@NonNull QProperty<?> property, @NonNull Number value);

    Update dec(@NonNull QProperty<?> property);

    Update decr(@NonNull QProperty<?> property, @NonNull Number value);


    @Override
    default void apply(Node parent, String expression) {
        final Document document = parent.getOwnerDocument();
        final Element set = document.createElement("set");
        this.forEach(item -> item.apply(set, expression));
        parent.appendChild(set);
    }
}
