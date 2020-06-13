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
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;

import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020-04-18 15:41:52
 * @since 1.0
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

