/*
 * Copyright 2020-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ifinalframework.data.auto.query;

import org.ifinalframework.data.auto.entity.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public final class QEntity {

    private final String packageName;

    private final String name;

    private final String simpleName;

    private final Entity entity;

    private final List<QProperty> properties;

    private final QProperty idProperty;

    private QEntity(final Builder builder) {

        this.entity = builder.entity;
        this.packageName = builder.packageName;
        this.simpleName = builder.simpleName;
        this.name = builder.packageName + "." + builder.simpleName;
        this.properties = builder.properties;
        this.idProperty = builder.idProperty;
    }

    public static Builder builder(final Entity entity) {

        return new Builder(entity);
    }

    public Entity getEntity() {
        return entity;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getName() {
        return name;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public List<QProperty> getProperties() {
        return properties;
    }

    public QProperty getIdProperty() {
        return idProperty;
    }

    /**
     * Builder.
     */
    public static class Builder implements org.ifinalframework.util.Builder<QEntity> {

        private final Entity entity;

        private final List<QProperty> properties = new ArrayList<>();

        private String packageName;

        private String simpleName;

        private QProperty idProperty;

        public Builder(final Entity entity) {

            this.entity = entity;
        }

        public Builder packageName(final String packageName) {

            this.packageName = packageName;
            return this;
        }

        public Builder name(final String simpleName) {

            this.simpleName = simpleName;
            return this;
        }

        public Builder addProperty(final QProperty property) {

            if (property.isIdProperty()) {
                idProperty = property;
                properties.add(0, property);
            } else {
                this.properties.add(property);
            }
            return this;
        }

        @Override
        public QEntity build() {
            return new QEntity(this);
        }

    }

}
