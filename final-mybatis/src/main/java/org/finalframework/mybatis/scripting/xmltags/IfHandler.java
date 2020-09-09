

package org.finalframework.mybatis.scripting.xmltags;


import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.scripting.xmltags.IfSqlNode;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;

import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020-04-18 15:42:26
 * @since 1.0
 */
class IfHandler implements NodeHandler {
    private final XMLScriptBuilder xmlScriptBuilder;

    public IfHandler(XMLScriptBuilder xmlScriptBuilder) {
        this.xmlScriptBuilder = xmlScriptBuilder;
        // Prevent Synthetic Access
    }

    @Override
    public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
        MixedSqlNode mixedSqlNode = xmlScriptBuilder.parseDynamicTags(nodeToHandle);
        String test = nodeToHandle.getStringAttribute("test");
        IfSqlNode ifSqlNode = new IfSqlNode(mixedSqlNode, test);
        targetContents.add(ifSqlNode);
    }
}

