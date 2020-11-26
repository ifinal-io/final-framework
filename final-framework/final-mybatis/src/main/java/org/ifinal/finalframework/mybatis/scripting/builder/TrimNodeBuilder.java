package org.ifinal.finalframework.mybatis.scripting.builder;


import org.w3c.dom.Document;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class TrimNodeBuilder extends SqlNodeBuilder {
    public TrimNodeBuilder(Document document) {
        super(document, "trim");
    }

    public TrimNodeBuilder prefix(String prefix) {
        setAttribute("prefix", prefix);
        return this;
    }

    public TrimNodeBuilder suffix(String suffix) {
        setAttribute("suffix", suffix);
        return this;
    }

}

