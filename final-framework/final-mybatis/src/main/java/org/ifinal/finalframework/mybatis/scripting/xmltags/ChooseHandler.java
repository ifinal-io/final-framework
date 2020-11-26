package org.ifinal.finalframework.mybatis.scripting.xmltags;


import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.scripting.xmltags.ChooseSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
class ChooseHandler implements NodeHandler {
    private final XMLScriptBuilder xmlScriptBuilder;

    public ChooseHandler(XMLScriptBuilder xmlScriptBuilder) {
        this.xmlScriptBuilder = xmlScriptBuilder;
        // Prevent Synthetic Access
    }

    @Override
    public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
        List<SqlNode> whenSqlNodes = new ArrayList<>();
        List<SqlNode> otherwiseSqlNodes = new ArrayList<>();
        handleWhenOtherwiseNodes(nodeToHandle, whenSqlNodes, otherwiseSqlNodes);
        SqlNode defaultSqlNode = getDefaultSqlNode(otherwiseSqlNodes);
        ChooseSqlNode chooseSqlNode = new ChooseSqlNode(whenSqlNodes, defaultSqlNode);
        targetContents.add(chooseSqlNode);
    }

    private void handleWhenOtherwiseNodes(XNode chooseSqlNode, List<SqlNode> ifSqlNodes, List<SqlNode> defaultSqlNodes) {
        List<XNode> children = chooseSqlNode.getChildren();
        for (XNode child : children) {
            String nodeName = child.getNode().getNodeName();
            NodeHandler handler = xmlScriptBuilder.getNodeHandlerMap().get(nodeName);
            if (handler instanceof IfHandler) {
                handler.handleNode(child, ifSqlNodes);
            } else if (handler instanceof OtherwiseHandler) {
                handler.handleNode(child, defaultSqlNodes);
            }
        }
    }

    private SqlNode getDefaultSqlNode(List<SqlNode> defaultSqlNodes) {
        SqlNode defaultSqlNode = null;
        if (defaultSqlNodes.size() == 1) {
            defaultSqlNode = defaultSqlNodes.get(0);
        } else if (defaultSqlNodes.size() > 1) {
            throw new BuilderException("Too many default (otherwise) elements in choose statement.");
        }
        return defaultSqlNode;
    }
}

