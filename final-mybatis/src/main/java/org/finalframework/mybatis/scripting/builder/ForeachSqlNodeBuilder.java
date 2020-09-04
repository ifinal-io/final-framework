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

package org.finalframework.mybatis.scripting.builder;


import org.w3c.dom.Document;

/**
 * <pre>
 *     <code>
 *         <foreach collection="collection" item="item" open="open" close="close" separator="separator"></foreach>
 *     </code>
 * </pre>
 *
 * @author likly
 * @version 1.0
 * @date 2020-06-05 17:26:41
 * @since 1.0
 */
public class ForeachSqlNodeBuilder extends SqlNodeBuilder {
    public ForeachSqlNodeBuilder(Document document) {
        super(document, "foreach");
    }

    public ForeachSqlNodeBuilder collection(String collection) {
        setAttribute("collection", collection);
        return this;
    }

    public ForeachSqlNodeBuilder item(String item) {
        setAttribute("item", item);
        return this;
    }

    public ForeachSqlNodeBuilder open(String open) {
        this.setAttribute("open", open);
        return this;
    }

    public ForeachSqlNodeBuilder close(String close) {
        this.setAttribute("close", close);
        return this;
    }

    public ForeachSqlNodeBuilder separator(String separator) {
        this.setAttribute("separator", separator);
        return this;
    }


}

