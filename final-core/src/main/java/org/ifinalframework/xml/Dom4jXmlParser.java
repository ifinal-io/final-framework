package org.ifinalframework.xml;

import org.dom4j.Document;

import java.util.Optional;

/**
 * @author ilikly
 * @version 1.2.4
 **/
public class Dom4jXmlParser implements XmlParser<Document> {
    @Override
    public Element parse(Document document) {
        return parse(null, document.getRootElement());
    }

    private Element parse(Element parent, org.dom4j.Element element) {
        Element e = new Element(element.getName());
        Optional.ofNullable(parent).ifPresent(it -> it.addElement(e));

        element.attributes().forEach(it -> e.addAttribute(it.getName(), it.getValue()));

        if(element.isTextOnly()){
            e.setValue(element.getTextTrim());
        }else {
            element.elements().forEach(it -> parse(e, it));
        }

        e.setValue(element.getTextTrim());

        return e;
    }
}
