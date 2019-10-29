package org.finalframework.coding.mapper.builder;

import org.finalframework.coding.entity.Entity;
import org.finalframework.coding.entity.Property;
import org.springframework.lang.NonNull;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.lang.model.element.TypeElement;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-11 15:14:09
 * @since 1.0
 */
public interface XmlMapperBuilder {
    void build(@NonNull Node root, @NonNull Document document, @NonNull TypeElement mapper, @NonNull Entity<Property> entity);
}
