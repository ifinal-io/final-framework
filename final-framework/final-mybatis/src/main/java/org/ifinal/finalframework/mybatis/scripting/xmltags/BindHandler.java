package org.ifinal.finalframework.mybatis.scripting.xmltags;


import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.StaticTextSqlNode;
import org.apache.ibatis.scripting.xmltags.TextSqlNode;
import org.apache.ibatis.scripting.xmltags.VarDeclSqlNode;

import java.util.List;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class BindHandler implements NodeHandler {
    private final XMLScriptBuilder xmlScriptBuilder;

    public BindHandler(XMLScriptBuilder xmlScriptBuilder) {
        this.xmlScriptBuilder = xmlScriptBuilder;
        // Prevent Synthetic Access
    }

    @Override
    public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
        final String name = nodeToHandle.getStringAttribute("name");
        final String expression = nodeToHandle.getStringAttribute("value");
        final VarDeclSqlNode node = new VarDeclSqlNode(name, expression);
        targetContents.add(node);

        String data = nodeToHandle.getStringBody("");
        if (data != null && !data.isEmpty()) {
            TextSqlNode textSqlNode = new TextSqlNode(data);
            if (textSqlNode.isDynamic()) {
                targetContents.add(textSqlNode);
                xmlScriptBuilder.setDynamic(true);
            } else {
                targetContents.add(new StaticTextSqlNode(data));
            }
        }


    }
}

