package org.finalframework.mybatis.sql;


import org.finalframework.mybatis.scripting.builder.ForeachSqlNodeBuilder;
import org.finalframework.mybatis.scripting.builder.TrimNodeBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2020-06-05 17:04:05
 * @since 1.0
 */
public class ScriptMapperHelper {
    private final Document document;

    public ScriptMapperHelper(Document document) {
        this.document = document;
    }

    public TrimNodeBuilder trim() {
        return new TrimNodeBuilder(document);
    }

    public ForeachSqlNodeBuilder foreach() {
        return new ForeachSqlNodeBuilder(document);
    }

    public Node cdata(String data) {
        return document.createCDATASection(data);
    }

    public Node bind(String name, String value) {
        final Element bind = document.createElement("bind");
        bind.setAttribute("name", name);
        bind.setAttribute("value", value);
        return bind;
    }


    public String formatBindValue(String prefix, String path) {
        if (path.contains(".")) {
            final String[] paths = path.split("\\.");
            List<String> isNulls = new ArrayList<>(paths.length);

            final StringBuilder builder = new StringBuilder();
            builder.append(prefix);
            for (String item : paths) {
                builder.append(".").append(item);
                isNulls.add(builder.toString());
            }


            final String isNull = isNulls.stream().map(item -> String.format("%s == null", item))
                    .collect(Collectors.joining(" || "));
            return String.format("%s ? null : %s.%s", isNull, prefix, path);
        }
        return String.format("%s.%s", prefix, path);
    }

}

