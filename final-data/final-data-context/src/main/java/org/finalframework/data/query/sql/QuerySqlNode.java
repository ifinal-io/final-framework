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

package org.finalframework.data.query.sql;


import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.finalframework.core.Assert;
import org.finalframework.data.annotation.query.Criterion;
import org.finalframework.data.annotation.query.MeteData;
import org.finalframework.data.annotation.query.OR;
import org.finalframework.data.mapping.Entity;
import org.finalframework.data.query.QEntity;
import org.finalframework.data.annotation.query.AndOr;
import org.finalframework.data.util.Velocities;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2020-07-14 18:05:29
 * @since 1.0
 */
@Slf4j
public class QuerySqlNode {
    public void apply(Node parent, String expression, Class<?> entity, Class<?> query) {
        final Document document = parent.getOwnerDocument();

        final QEntity<?, ?> properties = QEntity.from(entity);

        final Element where = document.createElement("where");

        AndOr andOr = entity.isAnnotationPresent(OR.class) ? AndOr.OR : AndOr.AND;


        Entity.from(query)
                .stream()
                .map(property -> {
                    final Criterion criterion = property.findAnnotation(Criterion.class);
                    final String xml = Arrays.stream(criterion.value()).map(String::trim).collect(Collectors.joining());

                    final String path = Assert.isBlank(criterion.property()) ? property.getName() : criterion.property();

                    final MeteData meteData = MeteData.builder().andOr(andOr)
                            .column(properties.getProperty(path).getColumn())
                            .value(String.format("%s.%s", expression, property.getName()))
                            .path(String.format("%s.%s", expression, property.getName()))
                            .build();
                    final String value = Velocities.getValue(xml, meteData);

                    return new XPathParser(value).evalNode("//script");

                }).forEach(script -> {

            final List<XNode> children = script.getChildren();
            children.forEach(node -> {
                final Node importNode = where.getOwnerDocument().importNode(node.getNode(), true);
                where.appendChild(importNode);
            });
        });


        parent.appendChild(where);


    }
}

