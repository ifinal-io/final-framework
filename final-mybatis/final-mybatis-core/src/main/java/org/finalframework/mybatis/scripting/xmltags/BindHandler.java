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


import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.StaticTextSqlNode;
import org.apache.ibatis.scripting.xmltags.TextSqlNode;
import org.apache.ibatis.scripting.xmltags.VarDeclSqlNode;

import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020-04-18 15:41:05
 * @since 1.0
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

