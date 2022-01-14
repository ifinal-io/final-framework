package org.ifinalframework.xml;

import org.w3c.dom.*;

import java.util.Optional;

/**
 * @author likly
 * @version 1.2.4
 **/
public class DomXmlParser implements XmlParser<Document> {

    @Override
    public Element parse(Document document) {
        return parseElement(null,document.getDocumentElement());
    }

    private Element parseElement(Element parent, org.w3c.dom.Element element){
        Element e = new Element(element.getTagName());

        NamedNodeMap attributes = element.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
            Attr attr = (Attr) attributes.item(i);
            e.addAttribute(attr.getName(),attr.getValue());
        }
        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            if(item instanceof org.w3c.dom.Element){
                parseElement(e, (org.w3c.dom.Element) item);
            }else if(item instanceof org.w3c.dom.Text){
                e.setValue(item.getNodeValue());
            }
        }


        Optional.ofNullable(parent).ifPresent(it -> it.addElement(e));

        return e;
    }
}
