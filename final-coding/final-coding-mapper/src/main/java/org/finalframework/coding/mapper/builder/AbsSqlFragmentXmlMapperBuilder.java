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

package org.finalframework.coding.mapper.builder;

import org.finalframework.coding.entity.Entity;
import org.finalframework.coding.entity.Property;
import org.finalframework.coding.mapper.TypeHandlers;
import org.finalframework.core.parser.xml.XNode;
import org.finalframework.core.parser.xml.XPathParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-14 16:48:46
 * @see SqlTableFragmentXmlMapperBuilder
 * @see SqlSelectColumnsFragmentXmlMapperBuilder
 * @see SqlWhereIdFragmentXmlMapperBuilder
 * @see SqlWhereIdsFragmentXmlMapperBuilder
 * @see SqlWhereCriteriaFragmentXmlMapperBuilder
 * @see SqlGroupFragmentXmlMapperBuilder
 * @see SqlOrderFragmentXmlMapperBuilder
 * @see SqlLimitFragmentXmlMapperBuilder
 * @see SqlQueryFragmentXmlMapperBuilder
 * @since 1.0
 */
public abstract class AbsSqlFragmentXmlMapperBuilder extends AbsXmlMapperBuilder implements SqlFragmentXmlMapperBuilder {
    public AbsSqlFragmentXmlMapperBuilder(TypeHandlers typeHandlers) {
        super(typeHandlers);
    }

    @Override
    public final void build(Node root, Document document, Entity entity) {
        XPathParser parser = new XPathParser(document);
        XNode node = parser.evalNode("//*[@id='" + id() + "']");
        if (node != null) {
//            root.removeChild(node.getNode());
            return;
        }

        buildSqlFragmentStartComment(root, document, entity);
        root.appendChild(buildSqlFragment(document, entity));
        buildSqlFragmentEndComment(root, document, entity);
    }


    protected void buildSqlFragmentStartComment(Node root, Document document, Entity entity) {
        String fragmentName = id().replaceAll("[A-Z]", " $0").toUpperCase();
        root.appendChild(document.createComment("=============================================================================================================="));
        root.appendChild(document.createComment(String.format("=====%-36s" + GENERATED_TAG + "%36s=====", fragmentName, fragmentName).replaceAll(" ", "=")));
        root.appendChild(document.createComment("=============================================================================================================="));
    }

    protected void buildSqlFragmentEndComment(Node root, Document document, Entity entity) {

    }

    protected abstract Element buildSqlFragment(Document document, Entity entity);
}
