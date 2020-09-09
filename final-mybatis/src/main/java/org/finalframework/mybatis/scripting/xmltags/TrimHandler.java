

package org.finalframework.mybatis.scripting.xmltags;


import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.TrimSqlNode;

import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020-04-18 15:43:41
 * @since 1.0
 */
class TrimHandler implements NodeHandler {
    private final XMLScriptBuilder xmlScriptBuilder;

    public TrimHandler(XMLScriptBuilder xmlScriptBuilder) {
        this.xmlScriptBuilder = xmlScriptBuilder;
        // Prevent Synthetic Access
    }

    @Override
    public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
        MixedSqlNode mixedSqlNode = xmlScriptBuilder.parseDynamicTags(nodeToHandle);
        String prefix = nodeToHandle.getStringAttribute("prefix");
        String prefixOverrides = nodeToHandle.getStringAttribute("prefixOverrides");
        String suffix = nodeToHandle.getStringAttribute("suffix");
        String suffixOverrides = nodeToHandle.getStringAttribute("suffixOverrides");
        TrimSqlNode trim = new TrimSqlNode(xmlScriptBuilder.getConfiguration(), mixedSqlNode, prefix, prefixOverrides, suffix, suffixOverrides);
        targetContents.add(trim);
    }
}

