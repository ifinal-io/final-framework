package org.finalframework.mybatis.xml.element;

import lombok.Getter;
import org.finalframework.core.Assert;
import org.finalframework.core.Streamable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-22 00:45:11
 * @since 1.0
 */
@Getter
public class ResultMap implements Element, Streamable<Element>, Iterable<Element>, Serializable {

    private static final String NAME = "resultMap";

    private final String id;
    private final Class type;
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
        this.elements = builder.elements.isEmpty() ? Collections.emptyList() : Collections.unmodifiableList(builder.elements);
    }

    public static Builder builder(String id, Class type) {
        return new Builder(id, type);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("<resultMap");

        if (Assert.nonEmpty(id)) {
            builder.append(" id=\"").append(id).append("\"");
        }

        if (Assert.nonEmpty(type)) {
            builder.append(" type=\"").append(type.getCanonicalName()).append("\"");
        }

        builder.append("></resultMap>");


        return builder.toString();
    }

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public final ElementType type() {
        return ElementType.RESULT_MAP;
    }

    @Override
    public Stream<Element> stream() {
        return elements.stream();
    }

    @Override
    public Iterator<Element> iterator() {
        return elements.iterator();
    }

    public static class Builder implements org.finalframework.core.Builder<ResultMap> {
        private final String id;
        private final Class type;
        private final List<Result> results = new ArrayList<>();
        private final List<Association> associations = new ArrayList<>();
        private final List<Element> elements = new ArrayList<>();
        private Boolean autoMapping;

        private Builder(String id, Class type) {
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


        public Builder autoMapping(boolean autoMapping) {
            this.autoMapping = autoMapping;
            return this;
        }

        @Override
        public ResultMap build() {
            return new ResultMap(this);
        }
    }

}
