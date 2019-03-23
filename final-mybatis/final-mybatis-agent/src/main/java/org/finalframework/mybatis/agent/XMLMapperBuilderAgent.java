package org.finalframework.mybatis.agent;

import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.parsing.XNode;
import org.finalframework.data.mapping.Entity;
import org.finalframework.data.repository.Repository;
import org.finalframework.mybatis.EntityHolderCache;
import org.finalframework.mybatis.builder.FinalXmlMapperBuilder;
import org.finalframework.mybatis.builder.XmlMapperBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-21 23:40:31
 * @see org.apache.ibatis.builder.xml.XMLMapperBuilder
 * @since 1.0
 */
@SuppressWarnings("unused")
public class XMLMapperBuilderAgent {
    private static final Logger logger = LoggerFactory.getLogger(XMLMapperBuilderAgent.class);
    private static final EntityHolderCache cache = new EntityHolderCache();
    private static final XmlMapperBuilder xmlMapperBuilder = new FinalXmlMapperBuilder();

    @SuppressWarnings("unused")
    public static void configurationDefaultElement(XNode context) {
        String namespace = context.getStringAttribute("namespace");
        if (namespace == null || namespace.equals("")) {
            throw new BuilderException("Mapper's namespace cannot be empty");
        }
        try {
            Class mapperClass = Class.forName(namespace);
            if (Repository.class.isAssignableFrom(mapperClass)) {
                Entity<?> entity = cache.get(mapperClass);
                final long itemStart = System.currentTimeMillis();
                final Collection<Element> elements = xmlMapperBuilder.build(context.getNode().getOwnerDocument(), mapperClass, entity);
                if (!elements.isEmpty()) {
                    elements.forEach(element -> context.getNode().appendChild(element));
                }
                logger.info("构建 Mapper : {} 用时：{}", mapperClass.getCanonicalName(), (System.currentTimeMillis() - itemStart));
                loggerMapper(context.getNode().getOwnerDocument());
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Mapper cannot be found:mapper=" + namespace);
        }
    }

    private static void loggerMapper(Document document) {
        String result = null;
        if (document != null) {
            StringWriter strWtr = new StringWriter();
            StreamResult strResult = new StreamResult(strWtr);
            TransformerFactory tfac = TransformerFactory.newInstance();
            try {
                javax.xml.transform.Transformer t = tfac.newTransformer();
                t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                t.setOutputProperty(OutputKeys.INDENT, "yes");
                t.setOutputProperty(OutputKeys.METHOD, "xml"); // xml, html,
                // text
                t.setOutputProperty(
                        "{http://xml.apache.org/xslt}indent-amount", "4");
                document.setXmlStandalone(true);
                t.transform(new DOMSource(document.getDocumentElement()),
                        strResult);
            } catch (Exception e) {
                System.err.println("XML.toString(Document): " + e);
            }
            result = strResult.getWriter().toString();
            try {
                strWtr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logger.info(result);
    }
}


