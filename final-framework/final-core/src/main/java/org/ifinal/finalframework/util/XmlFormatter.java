package org.ifinal.finalframework.util;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * XmlFormatter.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public final class XmlFormatter {

    private static final int DEFAULT_INDENT = 4;

    private XmlFormatter() {

    }

    public static String format(String xml) {
        return format(xml, DEFAULT_INDENT);
    }

    public static String format(String xml, int indent) {
        try {
            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer = transFactory.newTransformer();

            if (indent > 0) {
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", String.valueOf(indent));
            }

            transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            transformer.transform(new StreamSource(new StringReader(xml)), new StreamResult(stream));
            return stream.toString();

        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }

}
