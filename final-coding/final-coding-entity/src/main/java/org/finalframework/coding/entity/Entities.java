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
public class Entities implements Streamable<TypeElement>,Iterable<TypeElement>, Serializable {
    private final Set<TypeElement> entities;

    private Entities(Builder builder) {
        builder.entities.sort(new Comparator<TypeElement>() {
            @Override
            public int compare(TypeElement o1, TypeElement o2) {
                return o1.getQualifiedName().toString().compareTo(o2.getQualifiedName().toString());
            }
        });
        entities = new HashSet<>(builder.entities);
    }

    public static Builder builder(){
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
            this.entities.add(entity);
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

