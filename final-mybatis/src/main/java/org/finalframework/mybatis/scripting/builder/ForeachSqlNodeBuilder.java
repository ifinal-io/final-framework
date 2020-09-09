

package org.finalframework.mybatis.scripting.builder;


import org.w3c.dom.Document;

/**
 * <pre>
 *     <code>
 *         <foreach collection="collection" item="item" open="open" close="close" separator="separator"></foreach>
 *     </code>
 * </pre>
 *
 * @author likly
 * @version 1.0
 * @date 2020-06-05 17:26:41
 * @since 1.0
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

