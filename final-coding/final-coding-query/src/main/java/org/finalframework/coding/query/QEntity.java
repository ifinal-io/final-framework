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

package org.finalframework.coding.query;

import org.finalframework.coding.annotation.Template;
import org.finalframework.coding.entity.Entity;
import org.finalframework.coding.entity.Property;
import org.finalframework.coding.file.JavaSource;

import java.util.ArrayList;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-29 13:24
 * @since 1.0
 */
@Template("query/entity.jvm")
public class QEntity implements JavaSource {
    private final String packageName;
    private final String name;
    private final String simpleName;
    private final Entity entity;
    private final List<QProperty> properties;
    private final QProperty idProperty;

    private QEntity(Builder builder) {
        this.entity = builder.entity;
        this.packageName = builder.packageName;
        this.simpleName = builder.simpleName;
        this.name = builder.packageName + "." + builder.simpleName;
        this.properties = builder.properties;
        this.idProperty = builder.idProperty;
    }

    public static Builder builder(Entity entity) {
        return new Builder(entity);
    }

    public Entity getEntity() {
        return entity;
    }

    @Override
    public String getPackageName() {
        return packageName;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSimpleName() {
        return simpleName;
    }

    public List<QProperty> getProperties() {
        return properties;
    }

    public QProperty getIdProperty() {
        return idProperty;
    }

    public static class Builder implements org.finalframework.core.Builder<QEntity> {
        private final Entity entity;
        private String packageName;
        private String simpleName;
        private List<QProperty> properties = new ArrayList<>();
        private QProperty idProperty;

        public Builder(Entity entity) {
            this.entity = entity;
        }

        public Builder packageName(String packageName) {
            this.packageName = packageName;
            return this;
        }

        public Builder name(String simpleName) {
            this.simpleName = simpleName;
            return this;
        }

        public Builder addProperty(QProperty property) {
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
