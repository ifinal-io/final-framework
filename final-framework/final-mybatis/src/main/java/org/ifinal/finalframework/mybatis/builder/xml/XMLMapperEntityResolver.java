package org.ifinal.finalframework.mybatis.builder.xml;


import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class XMLMapperEntityResolver implements EntityResolver {
    InputSource source = new InputSource(new StringReader("<?xml version='1.0' encoding='UTF-8'?>"));

    @Override
    public InputSource resolveEntity(final String publicId, final String systemId) throws SAXException, IOException {

        return source;
    }
}

