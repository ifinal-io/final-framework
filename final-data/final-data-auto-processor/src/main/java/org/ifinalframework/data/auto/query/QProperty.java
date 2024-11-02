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

import javax.lang.model.element.Element;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public final class QProperty {

    private static final Set<String> dateTypes = new HashSet<>();

    static {
        dateTypes.add(Date.class.getCanonicalName());
        dateTypes.add(LocalDateTime.class.getCanonicalName());
    }

    private final String path;

    private final String name;

    private final Element element;

    private final boolean idProperty;

    private QProperty(final Builder builder) {

        this.path = builder.path;
        this.name = builder.name;
        this.element = builder.element;
        this.idProperty = builder.idProperty;
    }

    public static Builder builder(final String path, final String name) {

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

    /**
     * Builder.
     */
    public static final class Builder implements org.ifinalframework.util.Builder<QProperty> {

        private final String path;

        private final String name;

        private Element element;

        private boolean idProperty;

        private Builder(final String path, final String name) {

            this.path = path;
            this.name = name;
        }

        public Builder element(final Element element) {

            this.element = element;
            return this;
        }

        public Builder idProperty(final boolean idProperty) {

            this.idProperty = idProperty;
            return this;
        }

        @Override
        public QProperty build() {
            return new QProperty(this);
        }

    }

}
