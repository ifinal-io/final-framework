package org.finalframework.coding.mapper.xml;


import org.finalframework.coding.entity.Entity;
import org.finalframework.coding.mapper.TypeHandlers;
import org.finalframework.core.Streamable;

import javax.lang.model.element.TypeElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-10 19:17:52
 * @since 1.0
 */
public class ResultMap implements Element, Streamable<Element>, Iterable<Element>, Serializable {

    private static final String SUFFIX = "Map";
    private static final long serialVersionUID = -3451967492698999200L;

    private final String id;
    private final TypeElement type;
    private final Boolean autoMapping;
    private final List<Result> results;
    private final List<Association> associations;
    private final List<Element> elements;

    private ResultMap(Builder builder) {
        this.id = builder.id;
        this.type = builder.type;
        this.autoMapping = builder.autoMapping;
        this.results = builder.results.isEmpty() ? Collections.emptyList() : Collections.unmodifiableList(builder.results);
        this.associations = builder.associations.isEmpty() ? Collections.emptyList() : Collections.unmodifiableList(builder.associations);
        this.elements = builder.elements.isEmpty() ? Collections.emptyList() : Collections.unmodifiableList(builder.elements.stream().sorted().collect(Collectors.toList()));
    }


    public static ResultMap from(Entity entity, TypeHandlers typeHandlers) {
        ResultMap.Builder builder = new ResultMap.Builder(entity.getSimpleName() + SUFFIX, entity.getElement());
        entity.stream()
                .filter(it -> !it.isTransient() && !it.isVirtual() && !it.isWriteOnly())
                .forEach(property -> {
                    if (property.isReference()) {
                        builder.addAssociation(Association.from(property, property.toEntity(), typeHandlers));
                    } else {
                        builder.addResult(Result.from(entity, null, property, typeHandlers));
                    }
                });
//


        return builder.build();
    }


    @Override
    public final ElementType type() {
        return ElementType.RESULT_MAP;
    }

    public String getId() {
        return id;
    }

    public TypeElement getType() {
        return type;
    }

    public Boolean getAutoMapping() {
        return autoMapping;
    }

    public List<Result> getResults() {
        return results;
    }

    public List<Association> getAssociations() {
        return associations;
    }

    public List<Element> getElements() {
        return elements;
    }

    @Override
    public Stream<Element> stream() {
        return elements.stream();
    }

    @Override
    public Iterator<Element> iterator() {
        return elements.iterator();
    }

    private static final class Builder implements org.finalframework.core.Builder<ResultMap> {
        private final String id;
        private final TypeElement type;
        private final List<Result> results = new ArrayList<>();
        private final List<Association> associations = new ArrayList<>();
        private final List<Element> elements = new ArrayList<>();
        private Boolean autoMapping;

        public Builder(String id, TypeElement type) {
            this.id = id;
            this.type = type;
        }

        public Builder addResult(Result result) {
            this.results.add(result);
            this.elements.add(result);
            return this;
        }

        public Builder addAssociation(Association association) {
            this.associations.add(association);
            this.elements.add(association);
            return this;
        }

        public Builder autoMapping(Boolean autoMapping) {
            this.autoMapping = autoMapping;
            return this;
        }

        @Override
        public ResultMap build() {
            return new ResultMap(this);
        }
    }
}
