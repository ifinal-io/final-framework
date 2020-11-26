package org.ifinal.finalframework.mybatis.scripting.builder;


import org.ifinal.finalframework.util.Builder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class SqlNodeBuilder implements Builder<Element> {

    private final Document document;
    private final Element node;

    public SqlNodeBuilder(Document document, String name) {
        this.document = document;
        this.node = document.createElement(name);
    }

    protected void setAttribute(String name, String value) {
        this.node.setAttribute(name, value);
    }

    public SqlNodeBuilder appendChild(Node... children) {
        for (Node child : children) {
            this.node.appendChild(child);
        }
        return this;
    }


    @Override
    public Element build() {
        return node;
    }
}

