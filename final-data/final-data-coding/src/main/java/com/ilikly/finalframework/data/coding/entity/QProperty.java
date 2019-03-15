package com.ilikly.finalframework.data.coding.entity;

import java.io.Serializable;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-26 21:43:22
 * @since 1.0
 */
public class QProperty implements Serializable {

    private static final long serialVersionUID = 737464778206273859L;
    private final String path;
    private final String name;
    private final String type;
    private final String rawType;
    private final boolean idProperty;

    private QProperty(Builder builder) {
        this.path = builder.path;
        this.name = builder.name;
        this.type = builder.type;
        this.rawType = builder.rawType;
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

    public String getType() {
        return type;
    }

    public String getRawType() {
        return rawType;
    }

    public static class Builder implements com.ilikly.finalframework.core.Builder<QProperty> {

        private final String path;
        private final String name;
        private String type;
        private String rawType;
        private boolean idProperty;

        private Builder(String path, String name) {
            this.path = path;
            this.name = name;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder rawType(String rawType) {
//            System.out.println("----------" + rawType);
            if (rawType != null) {
                if (rawType.contains("::")) {
                    final int index = rawType.lastIndexOf("::");
                    rawType = rawType.substring(index + "::".length()).replace(")", "").trim();
                }

                if (rawType.contains(")")) {
                    final int index = rawType.lastIndexOf(")");
                    rawType = rawType.substring(index + 1);
                }

                this.rawType = rawType.replace("java.lang.", "");

            } else {
                this.rawType = "Object";
            }
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
