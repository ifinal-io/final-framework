package com.ilikly.finalframework.mybatis.builder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-13 12:51:19
 * @since 1.0
 */
public interface MapperBuilder {

    Element build(Document document);

}
