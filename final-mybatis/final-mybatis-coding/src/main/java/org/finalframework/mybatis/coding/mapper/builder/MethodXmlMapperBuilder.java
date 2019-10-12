package org.finalframework.mybatis.coding.mapper.builder;

import org.finalframework.data.coding.entity.Entity;
import org.finalframework.data.coding.entity.Property;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.lang.model.element.ExecutableElement;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-11 15:12:32
 * @since 1.0
 */
public interface MethodXmlMapperBuilder {

    boolean supports(@NonNull ExecutableElement method);

    @NonNull
    Element build(@NonNull ExecutableElement method, Document document, Entity<Property> entity);

    @Nullable
    default Collection<Element> sqlFragments(@NonNull ExecutableElement method, @NonNull Document document, @NonNull Entity<Property> entity) {
        return null;
    }
}
