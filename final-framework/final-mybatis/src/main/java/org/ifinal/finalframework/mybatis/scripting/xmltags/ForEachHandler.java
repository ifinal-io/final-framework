package org.ifinal.finalframework.mybatis.scripting.xmltags;


import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;

import java.util.List;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class ForEachHandler implements NodeHandler {
    private final XMLScriptBuilder xmlScriptBuilder;

    public ForEachHandler(XMLScriptBuilder xmlScriptBuilder) {
        this.xmlScriptBuilder = xmlScriptBuilder;
        // Prevent Synthetic Access
    }

    @Override
    public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
        MixedSqlNode mixedSqlNode = xmlScriptBuilder.parseDynamicTags(nodeToHandle);
        String collection = nodeToHandle.getStringAttribute("collection");
        String item = nodeToHandle.getStringAttribute("item");
        String index = nodeToHandle.getStringAttribute("index");
        String open = nodeToHandle.getStringAttribute("open");
        String close = nodeToHandle.getStringAttribute("close");
        String separator = nodeToHandle.getStringAttribute("separator");
        ForEachSqlNode forEachSqlNode = new ForEachSqlNode(xmlScriptBuilder.getConfiguration(), mixedSqlNode, collection, index, item, open, close, separator);
        targetContents.add(forEachSqlNode);
    }
}

