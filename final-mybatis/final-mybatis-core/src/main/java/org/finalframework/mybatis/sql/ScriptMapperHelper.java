package org.finalframework.mybatis.sql;


import org.finalframework.mybatis.scripting.builder.ForeachSqlNodeBuilder;
import org.finalframework.mybatis.scripting.builder.TrimNodeBuilder;
import org.w3c.dom.Document;

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


}

