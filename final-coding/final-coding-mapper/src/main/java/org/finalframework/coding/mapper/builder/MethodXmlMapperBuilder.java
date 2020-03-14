package org.finalframework.coding.mapper.builder;

import org.finalframework.coding.entity.Entity;
import org.finalframework.coding.entity.Property;
import org.springframework.lang.NonNull;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.lang.model.element.ExecutableElement;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-11 15:12:32
 * @since 1.0
 */
public interface MethodXmlMapperBuilder {

    boolean supports(@NonNull ExecutableElement method);

    void build(@NonNull Node root, @NonNull Document document, @NonNull ExecutableElement method, Entity entity);


}
