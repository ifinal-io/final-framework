package org.ifinal.finalframework.mybatis.scripting.xmltags;


import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.WhereSqlNode;

import java.util.List;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
class WhereHandler implements NodeHandler {
    private final XMLScriptBuilder xmlScriptBuilder;

    public WhereHandler(XMLScriptBuilder xmlScriptBuilder) {
        this.xmlScriptBuilder = xmlScriptBuilder;
        // Prevent Synthetic Access
    }

    @Override
    public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
        MixedSqlNode mixedSqlNode = xmlScriptBuilder.parseDynamicTags(nodeToHandle);
        WhereSqlNode where = new WhereSqlNode(xmlScriptBuilder.getConfiguration(), mixedSqlNode);
        targetContents.add(where);
    }
}

