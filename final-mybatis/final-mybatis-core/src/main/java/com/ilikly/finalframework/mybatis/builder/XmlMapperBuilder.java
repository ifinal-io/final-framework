package com.ilikly.finalframework.mybatis.builder;

import com.ilikly.finalframework.data.mapping.Entity;
import org.springframework.lang.NonNull;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-09 19:32:52
 * @since 1.0
 */
public interface XmlMapperBuilder {

    Collection<Element> build(@NonNull Document document, @NonNull Class<?> mapper, @NonNull Entity<?> entity);

}