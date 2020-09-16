package org.finalframework.mybatis.sql.script;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author likly
 * @version 1.0
 * @date 2020-06-05 22:38:54
 * @since 1.0
 */
public class CriteriaSqlNodeBuilder implements ScriptSqlNodeBuilder {
    private final Integer maxLevel;

    public CriteriaSqlNodeBuilder(Integer maxLevel) {
        this.maxLevel = maxLevel;
    }

    @Override
    public void build(Node script, String value) {
        final Document document = script.getOwnerDocument();

        final Element ifCriteriaNotNull = document.createElement("if");
        ifCriteriaNotNull.setAttribute("test", String.format("%s != null", value));


        script.appendChild(ifCriteriaNotNull);
    }


}

