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

package org.finalframework.coding.entity;


import org.finalframework.coding.annotation.Template;
import org.finalframework.core.Streamable;

import javax.lang.model.element.TypeElement;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Stream;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-27 12:36:23
 * @since 1.0
 */
@Template("entity/final.entities.vm")
public class Entities implements Streamable<TypeElement>, Iterable<TypeElement>, Serializable {
    private final Set<TypeElement> entities;

    private Entities(Builder builder) {
        builder.entities.sort(Comparator.comparing(o -> o.getQualifiedName().toString()));
        entities = new HashSet<>(builder.entities);
    }

    public static Builder builder() {
        return new Builder();
    }

    public Set<TypeElement> getEntities() {
        return entities;
    }

    @Override
    public Stream<TypeElement> stream() {
        return entities.stream();
    }

    @Override
    public Iterator<TypeElement> iterator() {
        return entities.iterator();
    }

    public static class Builder implements org.finalframework.core.Builder<Entities> {
        private final List<TypeElement> entities = new ArrayList<>(128);

        public Builder addEntity(TypeElement entity) {
            if (entity != null) {
                this.entities.add(entity);
            }
            return this;
        }

        public Builder addEntities(Collection<TypeElement> entities) {
            this.entities.addAll(entities);
            return this;
        }

        @Override
        public Entities build() {
            return new Entities(this);
        }
    }
}

