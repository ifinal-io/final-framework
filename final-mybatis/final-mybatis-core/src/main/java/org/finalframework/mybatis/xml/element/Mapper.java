package org.finalframework.mybatis.xml.element;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-22 00:57:30
 * @since 1.0
 */
public class Mapper implements Serializable {
    private final Class namespace;
    private final List<ResultMap> resultMaps;

    private Mapper(Builder builder) {
        this.namespace = builder.namespace;
        this.resultMaps = builder.resultMaps.isEmpty() ? null : Collections.unmodifiableList(builder.resultMaps);
    }

    public static Builder builder(Class namespace) {
        return new Builder(namespace);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("<mapper");
        if (namespace != null) {
            builder.append(" namespace=\"").append(namespace.getCanonicalName()).append("\"");
        }

        builder.append(">").append("</mapper>");
        return builder.toString();
    }

    public static class Builder implements org.finalframework.core.Builder<Mapper> {
        private final Class namespace;
        private final List<ResultMap> resultMaps = new ArrayList<>();

        private Builder(Class namespace) {
            this.namespace = namespace;
        }

        public Builder addResultMap(ResultMap resultMap) {
            resultMaps.add(resultMap);
            return this;
        }

        @Override
        public Mapper build() {
            return new Mapper(this);
        }
    }
}
