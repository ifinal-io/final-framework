package org.finalframework.mybatis.builder.mapper;


import org.finalframework.data.mapping.Entity;
import org.springframework.lang.NonNull;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-09 19:36:05
 * @since 1.0
 */
public interface ResultMapXmlMapperBuilder {
    Element build(@NonNull Document document, @NonNull Entity<?> entity);
}
