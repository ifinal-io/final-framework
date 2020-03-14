package org.finalframework.coding.mapper.builder;

import org.finalframework.coding.entity.Entity;
import org.finalframework.coding.entity.Property;
import org.finalframework.core.parser.xml.XNode;
import org.finalframework.core.parser.xml.XPathParser;
import org.springframework.lang.NonNull;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-14 16:44:53
 * @since 1.0
 */
public interface AbsResultMapXmlMapperBuilder extends ResultMapXmlMapperBuilder {

    @Override
    default void build(Node root, Document document, Entity entity) {

        XPathParser parser = new XPathParser(document);
        XNode node = parser.evalNode("//*[@id='" + entity.getSimpleName() + "Map" + "']");
        if (node != null) {
//            root.removeChild(node.getNode());
            return;
        }

        buildResultMapStartComment(root, document, entity);
        root.appendChild(buildResultMap(document, entity));
        buildResultMapEndComment(root, document, entity);
    }

    default void buildResultMapStartComment(@NonNull Node root, @NonNull Document document, @NonNull Entity entity) {

    }

    Element buildResultMap(@NonNull Document document, @NonNull Entity entity);

    default void buildResultMapEndComment(@NonNull Node root, @NonNull Document document, @NonNull Entity entity) {

    }

}
