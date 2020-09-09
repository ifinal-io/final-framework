

package org.finalframework.mybatis.builder.xml;


import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;

/**
 * @author likly
 * @version 1.0
 * @date 2020-04-18 16:09:24
 * @since 1.0
 */
public class XMLMapperEntityResolver implements EntityResolver {
    InputSource source = new InputSource(new StringReader("<?xml version='1.0' encoding='UTF-8'?>"));

    @Override
    public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
        return source;
    }
}

