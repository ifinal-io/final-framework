package org.finalframework.mybatis.coding.mapper.builder;

import org.finalframework.data.coding.entity.Entity;
import org.finalframework.data.coding.entity.Property;
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
    default void build(Node root, Document document, Entity<Property> entity) {
        buildResultMapStartComment(root, document, entity);
        root.appendChild(buildResultMap(document, entity));
        buildResultMapEndComment(root, document, entity);
    }

    default void buildResultMapStartComment(@NonNull Node root, @NonNull Document document, @NonNull Entity<Property> entity) {

    }

    Element buildResultMap(@NonNull Document document, @NonNull Entity<Property> entity);

    default void buildResultMapEndComment(@NonNull Node root, @NonNull Document document, @NonNull Entity<Property> entity) {

    }

}
