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
import org.finalframework.coding.file.JavaSource;
import org.finalframework.core.Assert;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-27 22:07:04
 * @since 1.0
 */
@Template("query/criteria.jvm")
public class Criteria implements JavaSource {
    public static final String DEFAULT_PREFIX = "add";
    private final String prefix;
    private final String packageName;
    private final String simpleName;
    private final String name;
    private final QEntity entity;

    private Criteria(Builder builder) {
        this.prefix = Assert.isNull(builder.prefix) ? DEFAULT_PREFIX : builder.prefix;
        this.packageName = builder.packageName;
        this.simpleName = builder.simpleName;
        this.name = builder.packageName + "." + builder.simpleName;
        this.entity = builder.entity;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getPrefix() {
        return prefix == null ? DEFAULT_PREFIX : prefix;
    }

    @Override
    public String getPackageName() {
        return packageName;
    }


    @Override
    public String getSimpleName() {
        return simpleName;
    }

    @Override
    public String getName() {
        return name;
    }

    public QEntity getEntity() {
        return entity;
    }

    public static class Builder implements org.finalframework.core.Builder<Criteria> {
        private String prefix;
        private String packageName;
        private String simpleName;
        private QEntity entity;

        public Builder prefix(String prefix) {
            this.prefix = prefix;
            return this;
        }

        public Builder packageName(String packageName) {
            this.packageName = packageName;
            return this;
        }

        public Builder simpleName(String simpleName) {
            this.simpleName = simpleName;
            return this;
        }

        public Builder entity(QEntity entity) {
            this.entity = entity;
            return this;
        }


        @Override
        public Criteria build() {
            return new Criteria(this);
        }
    }
}

