

package org.finalframework.mybatis.scripting.builder;


import org.w3c.dom.Document;

/**
 * @author likly
 * @version 1.0
 * @date 2020-06-05 17:21:44
 * @since 1.0
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

