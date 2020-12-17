package org.ifinal.finalframework.mybatis.sql;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
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
public class XmlFormatter {

    public static String format(String xml) {

        try {

            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transFormer = transFactory.newTransformer();
            transFormer.setOutputProperty(OutputKeys.INDENT, "yes");
            transFormer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
            transFormer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            transFormer.transform(new StreamSource(new StringReader(xml)), new StreamResult(stream));

            return new String(stream.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
