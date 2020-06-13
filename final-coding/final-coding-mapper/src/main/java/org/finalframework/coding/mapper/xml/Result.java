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

package org.finalframework.coding.mapper.xml;

import java.util.Optional;
import javax.lang.model.element.TypeElement;

import org.finalframework.coding.entity.Entity;
import org.finalframework.coding.entity.Property;
import org.finalframework.coding.mapper.TypeHandlers;
import org.finalframework.data.annotation.PrimaryKey;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-10 19:10:44
 * @since 1.0
 */

public class Result implements Element {

    private final String property;
    private final String column;
    private final TypeElement javaType;
    private final TypeElement typeHandler;
    private final boolean idResult;

    private Result(Builder builder) {
        this.property = builder.property;
        this.column = builder.column;
        this.javaType = builder.javaType;
        this.typeHandler = builder.typeHandler;
        this.idResult = builder.idResult;
    }

    public static Result from(Entity entity, Property referenceProperty, Property property, TypeHandlers typeHandlers) {
        final String column = typeHandlers.formatPropertyColumn(entity, referenceProperty, property);
        return new Builder(property.getName(), column)
                .javaType(property.getJavaTypeElement())
                .typeHandler(Optional.ofNullable(property.getTypeHandler()).orElse(typeHandlers.getTypeHandler(property)))
                .idResult(property.hasAnnotation(PrimaryKey.class))
                .build();
    }


    @Override
    public final ElementType type() {
        return idResult ? ElementType.ID_RESULT : ElementType.RESULT;
    }

    public String getProperty() {
        return property;
    }

    public String getColumn() {
        return column;
    }

    public TypeElement getJavaType() {
        return javaType;
    }

    public TypeElement getTypeHandler() {
        return typeHandler;
    }

    public boolean isIdResult() {
        return idResult;
    }

    public static class Builder implements org.finalframework.core.Builder<Result> {
        private final String property;
        private final String column;
        private TypeElement javaType;
        private TypeElement typeHandler;
        private boolean idResult = false;

        public Builder(String property, String column) {
            this.property = property;
            this.column = column;
        }

        public Builder javaType(TypeElement javaType) {
            this.javaType = javaType;
            return this;
        }

        public Builder typeHandler(TypeElement typeHandler) {
            this.typeHandler = typeHandler;
            return this;
        }

        public Builder idResult(boolean idResult) {
            this.idResult = idResult;
            return this;
        }


        @Override
        public Result build() {
            return new Result(this);
        }
    }
}
