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
import org.finalframework.core.Assert;
import org.finalframework.core.parser.xml.XNode;
import org.finalframework.core.parser.xml.XPathParser;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.lang.model.element.ExecutableElement;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-14 16:33:54
 * @see InsertMethodXmlMapperBuilder
 * @see UpdateMethodXmlMapperBuilder
 * @see DeleteMethodXmlMapperBuilder
 * @see SelectMethodXmlMapperBuilder
 * @see SelectIdsMethodXmlMapperBuilder
 * @see SelectCountMethodXmlMapperBuilder
 * @since 1.0
 */
public abstract class AbsMethodXmlMapperBuilder extends AbsXmlMapperBuilder implements MethodXmlMapperBuilder {


    public AbsMethodXmlMapperBuilder(TypeHandlers typeHandlers) {
        super(typeHandlers);
    }

    @Override
    public final void build(Node root, Document document, ExecutableElement method, Entity entity) {

        XPathParser parser = new XPathParser(document);
        XNode node = parser.evalNode("//*[@id='" + method.getSimpleName() + "']");
        if (node != null) {
//            root.removeChild(node.getNode());
            return;
        }

        buildMethodStartComment(root, document, method, entity);
        root.appendChild(buildMethodElement(method, document, entity));
        Collection<Element> fragments = buildMethodFragments(document, method, entity);
        if (Assert.nonEmpty(fragments)) {
            fragments.forEach(root::appendChild);
        }
        buildMethodEndComment(root, document, method, entity);
    }

    protected void buildMethodStartComment(Node root, Document document, @NonNull ExecutableElement method, Entity entity) {
        String methodName = method.getSimpleName().toString().replaceAll("[A-Z]", " $0").toUpperCase();
        root.appendChild(document.createComment("=============================================================================================================="));
        root.appendChild(document.createComment(String.format("=====%-36s" + GENERATED_TAG + "%36s=====", methodName, methodName).replaceAll(" ", "=")));
        root.appendChild(document.createComment("=============================================================================================================="));

    }


    private void buildMethodEndComment(Node root, Document document, @NonNull ExecutableElement method, Entity entity) {

    }


    @Nullable
    protected Collection<Element> buildMethodFragments(@NonNull Document document, @NonNull ExecutableElement method, @NonNull Entity entity) {
        return null;
    }


    protected abstract Element buildMethodElement(ExecutableElement method, Document document, Entity entity);
}
