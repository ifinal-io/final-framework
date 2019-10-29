package org.finalframework.coding.mapper.builder;

import org.finalframework.coding.entity.Entity;
import org.finalframework.coding.entity.Property;
import org.springframework.lang.NonNull;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-10 16:33:13
 * @since 1.0
 */
public interface SqlFragmentXmlMapperBuilder {

    String id();

    void build(@NonNull Node root, @NonNull Document document, @NonNull Entity<Property> entity);

}
