package org.finalframework.mybatis.coding.mapper.builder;

import org.finalframework.data.coding.entity.Entity;
import org.finalframework.data.coding.entity.Property;
import org.springframework.lang.NonNull;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.lang.model.element.TypeElement;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-11 15:14:09
 * @since 1.0
 */
public interface XmlMapperBuilder {
    Collection<Element> build(@NonNull Document document, @NonNull TypeElement mapper, @NonNull Entity<Property> entity);
}
