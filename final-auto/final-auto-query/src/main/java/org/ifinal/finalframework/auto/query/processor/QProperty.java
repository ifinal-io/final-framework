package org.ifinal.finalframework.auto.query.processor;


import javax.lang.model.element.Element;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class QProperty {

    private static final Set<String> dateTypes = new HashSet<>();

    static {
        dateTypes.add(Date.class.getCanonicalName());
        dateTypes.add(LocalDateTime.class.getCanonicalName());
    }

    private final String path;
    private final String name;
    private final Element element;
    private final boolean idProperty;

    private QProperty(Builder builder) {
        this.path = builder.path;
        this.name = builder.name;
        this.element = builder.element;
        this.idProperty = builder.idProperty;
    }

    public static Builder builder(String path, String name) {
        return new Builder(path, name);
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public boolean isIdProperty() {
        return idProperty;
    }


    public Element getElement() {
        return element;
    }

    public static class Builder implements org.ifinal.finalframework.util.Builder<QProperty> {

        private final String path;
        private final String name;
        private Element element;
        private boolean idProperty;

        private Builder(String path, String name) {
            this.path = path;
            this.name = name;
        }


        public Builder element(Element element) {
            this.element = element;
            return this;
        }


        public Builder idProperty(boolean idProperty) {
            this.idProperty = idProperty;
            return this;
        }


        @Override
        public QProperty build() {
            return new QProperty(this);
        }
    }

}
