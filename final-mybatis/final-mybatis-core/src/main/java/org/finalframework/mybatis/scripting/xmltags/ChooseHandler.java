/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.finalframework.mybatis.scripting.xmltags;


import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.scripting.xmltags.ChooseSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020-04-18 15:42:54
 * @since 1.0
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

