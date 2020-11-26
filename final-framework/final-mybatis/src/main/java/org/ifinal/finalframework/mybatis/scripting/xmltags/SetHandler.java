package org.ifinal.finalframework.mybatis.scripting.xmltags;


import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SetSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;

import java.util.List;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
class SetHandler implements NodeHandler {
    private final XMLScriptBuilder xmlScriptBuilder;

    public SetHandler(XMLScriptBuilder xmlScriptBuilder) {
        this.xmlScriptBuilder = xmlScriptBuilder;
        // Prevent Synthetic Access
    }

    @Override
    public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
        MixedSqlNode mixedSqlNode = xmlScriptBuilder.parseDynamicTags(nodeToHandle);
        SetSqlNode set = new SetSqlNode(xmlScriptBuilder.getConfiguration(), mixedSqlNode);
        targetContents.add(set);
    }
}

