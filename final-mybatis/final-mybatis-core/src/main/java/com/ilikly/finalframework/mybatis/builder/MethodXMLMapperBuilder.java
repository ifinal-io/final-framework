package com.ilikly.finalframework.mybatis.builder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-12 20:40:24
 * @since 1.0
 */
public interface MethodXMLMapperBuilder {
    Element build(Method method, Document document);
}
