package org.ifinalframework.xml;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import lombok.SneakyThrows;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author ilikly
 * @version 1.2.4
 **/
class DomXmlParserTest {

    @Test
    @SneakyThrows
    void parse() {

        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

        Document document = builder.parse(getClass().getResourceAsStream("/parse.xml"));
        DomXmlParser parser = new DomXmlParser();

        Element element = parser.parse(document);
        assertNotNull(element);

    }
}