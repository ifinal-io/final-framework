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

package org.finalframework.data.query;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-12 17:11:43
 * @since 1.0
 */
public interface Limit extends SqlNode {

    static Limit limit(Long offset, Long limit) {
        return new LimitImpl(offset, limit);
    }

    static Limit limit(Long limit) {
        return limit(null, limit);
    }


    Long getOffset();

    Long getLimit();

    /**
     * <pre>
     *     <code>
     *         <trim prefix="LIMIT">
     *              <if test="limit.offset != null">
     *                  #{limit.offset},
     *              </if>
     *              #{limit.limit}
     *         </trim>
     *     </code>
     * </pre>
     *
     * @param sql
     * @param expression
     */
    @Override
    default void apply(StringBuilder sql, String expression) {

        sql.append("<trim prefix=\"LIMIT\">")
                .append(String.format("<if test=\"%s.offset != null\">", expression))
                .append(String.format("#{%s.offset},", expression))
                .append("</if>")
                .append(String.format("<if test=\"%s.limit != null\">", expression))
                .append(String.format("#{%s.limit}", expression))
                .append("</if>")
                .append("</trim>");

    }
}
