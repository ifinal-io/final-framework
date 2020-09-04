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

package org.finalframework.data.query;

import lombok.Getter;
import lombok.NonNull;
import org.finalframework.data.query.enums.UpdateOperation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-25 11:31
 * @since 1.0
 */
@Getter
public class SimpleUpdateSetOperation implements UpdateSetOperation {
    private final QProperty<?> property;
    private final UpdateOperation operation;
    private final Object value;

    public SimpleUpdateSetOperation(@NonNull QProperty<?> property, @NonNull UpdateOperation operation, @NonNull Object value) {
        this.property = property;
        this.operation = operation;
        this.value = value;
    }

    public boolean isNull() {
        return null == value;
    }

    public QProperty<?> getProperty() {
        return property;
    }

    public UpdateOperation getOperation() {
        return operation;
    }

    public Object getValue() {
        return value;
    }


    @Override
    public void apply(StringBuilder sql, String expression) {

        sql.append("<trim>");
        sql.append(property.getColumn());


        switch (operation) {
            case EQUAL:
                // column = #{expression.value,javaType=?,typeHandler=?}
                sql.append(" = ").append("#{").append(expression).append(".value");

                sql.append(",javaType=").append(property.getType().getCanonicalName());

                if (property.getTypeHandler() != null) {
                    sql.append(",typeHandler=").append(property.getTypeHandler().getCanonicalName());
                }

                sql.append("},");

                break;

            case INC:
            case INCR:
                // column = column + #{expression.value}
                sql.append(" = ").append(property.getColumn()).append(" + #{").append(expression).append(".value},");
                break;

            case DEC:
            case DECR:
                // column = column - #{expression.value}
                sql.append(" = ").append(property.getColumn()).append(" - #{").append(expression).append(".value},");
                break;
        }

        sql.append("</trim>");

    }

}
