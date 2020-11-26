package org.ifinal.finalframework.mybatis.scripting.builder;


import org.w3c.dom.Document;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class ForeachSqlNodeBuilder extends SqlNodeBuilder {
    public ForeachSqlNodeBuilder(Document document) {
        super(document, "foreach");
    }

    public ForeachSqlNodeBuilder collection(String collection) {
        setAttribute("collection", collection);
        return this;
    }

    public ForeachSqlNodeBuilder item(String item) {
        setAttribute("item", item);
        return this;
    }

    public ForeachSqlNodeBuilder open(String open) {
        this.setAttribute("open", open);
        return this;
    }

    public ForeachSqlNodeBuilder close(String close) {
        this.setAttribute("close", close);
        return this;
    }

    public ForeachSqlNodeBuilder separator(String separator) {
        this.setAttribute("separator", separator);
        return this;
    }


}

