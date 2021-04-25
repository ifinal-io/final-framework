/*
 * Copyright 2020-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

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

    public static String format(final String xml) {
        return format(xml, DEFAULT_INDENT);
    }

    public static String format(final String xml, final int indent) {
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
