package org.ifinalframework.xml;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.junit.jupiter.api.Test;

import lombok.SneakyThrows;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author ilikly
 * @version 1.2.4
 **/
class Dom4jXmlParserTest {

    @Test
    @SneakyThrows
    void parse() {
        SAXReader reader = new SAXReader();
        Document document = reader.read(getClass().getResourceAsStream("/parse.xml"));
        Element element = new Dom4jXmlParser().parse(document);
        assertNotNull(element);
    }

}