package org.finalframework.coding.mapper;


import org.finalframework.coding.annotation.Template;
import org.finalframework.core.Streamable;

import javax.lang.model.element.TypeElement;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Stream;

/**
 * @author likly
 * @version 1.0
 * @date 2019-12-17 18:26:17
 * @since 1.0
 */
@Template("mapper/final.mappers.vm")
public class Mappers implements Streamable<TypeElement>, Iterable<TypeElement>, Serializable {
    private final Set<TypeElement> mappers;

    private Mappers(Builder builder) {
        builder.mappers.sort(new Comparator<TypeElement>() {
            @Override
            public int compare(TypeElement o1, TypeElement o2) {
                return o1.getQualifiedName().toString().compareTo(o2.getQualifiedName().toString());
            }
        });
        mappers = new HashSet<>(builder.mappers);
    }

    public static Builder builder() {
        return new Builder();
    }

    public Set<TypeElement> getMappers() {
        return mappers;
    }

    @Override
    public Stream<TypeElement> stream() {
        return mappers.stream();
    }

    @Override
    public Iterator<TypeElement> iterator() {
        return mappers.iterator();
    }

    public static class Builder implements org.finalframework.core.Builder<Mappers> {
        private final Set<String> mapperClassNames = new HashSet<>();
        private final List<TypeElement> mappers = new ArrayList<>(128);

        public Builder addMapper(TypeElement mapper) {
            if (mapperClassNames.contains(mapper.getQualifiedName().toString())) return this;
            this.mappers.add(mapper);
            this.mapperClassNames.add(mapper.getQualifiedName().toString());
            return this;
        }

        public Builder addMappers(Collection<TypeElement> mappers) {
            mappers.forEach(this::addMapper);
            return this;
        }

        @Override
        public Mappers build() {
            return new Mappers(this);
        }
    }
}

