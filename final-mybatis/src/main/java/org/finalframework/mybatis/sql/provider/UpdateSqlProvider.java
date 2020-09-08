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

package org.finalframework.mybatis.sql.provider;


import org.apache.ibatis.builder.annotation.ProviderContext;
import org.finalframework.annotation.IEntity;
import org.finalframework.annotation.data.Metadata;
import org.finalframework.core.Assert;
import org.finalframework.data.query.QEntity;
import org.finalframework.data.query.Query;
import org.finalframework.data.query.Update;
import org.finalframework.data.query.UpdateSetOperation;
import org.finalframework.data.util.Velocities;
import org.finalframework.mybatis.sql.AbsMapperSqlProvider;
import org.finalframework.mybatis.sql.ScriptMapperHelper;

import java.util.Collection;
import java.util.Map;

/**
 * <pre>
 *     <code>
 *         <script>
 *             <trim prefix="UPDATE">
 *                  ${table}
 *             </trim>
 *             <set>
 *
 *             </set>
 *             <where>
 *                 id in
 *                 <foreach collections="ids" item="id" open="(" close=")>#{id}</foreach>"
 *             </where>
 *         </script>
 *     </code>
 * </pre>
 *
 * @author likly
 * @version 1.0
 * @date 2020-04-15 00:23:52
 * @see org.finalframework.mybatis.mapper.AbsMapper#update(String, Class, IEntity, Update, boolean, Collection, Query)
 * @since 1.0
 */
public class UpdateSqlProvider implements AbsMapperSqlProvider, ScriptSqlProvider {
    private static final String DEFAULT_WRITER = "#{${value}#if($javaType),javaType=$!{javaType.canonicalName}#end#if($typeHandler),typeHandler=$!{typeHandler.canonicalName}#end}";

    /**
     * @see org.finalframework.mybatis.mapper.AbsMapper#update(String, Class, IEntity, Update, boolean, Collection,
     * Query)
     */
    public String update(ProviderContext context, Map<String, Object> parameters) {
        return provide(context, parameters);
    }


    @Override
    public void doProvide(StringBuilder sql, ProviderContext context, Map<String, Object> parameters) {

        final Object ids = parameters.get("ids");
        final Object query = parameters.get("query");
        final Class<?> view = (Class<?>) parameters.get("view");
        final boolean selective = Boolean.TRUE.equals(parameters.get("selective"));

        final Class<?> entity = getEntityClass(context.getMapperType());
        final QEntity<?, ?> properties = QEntity.from(entity);

        /*
         * <trim prefix="UPDATE">
         *      ${table}
         * </trim>
         */
        sql.append("<trim prefix=\"UPDATE\">").append("${table}").append("</trim>");


        sql.append("<set>");

        if (parameters.containsKey("entity") && parameters.get("entity") != null) {
            properties.stream()
                    .filter(property -> property.isWriteable() && property.hasView(view))
                    .forEach(property -> {

                        final Metadata metadata = new Metadata();

                        metadata.setProperty(property.getName());
                        metadata.setColumn(property.getColumn());
                        metadata.setValue("entity." + property.getName());
                        metadata.setJavaType(property.getType());
                        metadata.setTypeHandler(property.getTypeHandler());

                        final String writer = Assert.isBlank(property.getWriter()) ? DEFAULT_WRITER : property.getWriter();
                        final String value = Velocities.getValue(writer, metadata);

                        final String test = ScriptMapperHelper.formatTest("entity", property.getPath(), selective);

                        if (test == null) {
                            /*
                              ${column} = #{value}
                             */
                            sql.append(property.getColumn()).append(" = ").append(value).append(",");
                        } else {

                            /*
                              <if test="${test}">
                                   ${column} = #{value}
                              </if>
                             */

                            sql.append("<if test=\"").append(test).append("\">");
                            sql.append(property.getColumn()).append(" = ").append(value).append(",");
                            sql.append("</if>");

                        }


                    });
        } else if (parameters.containsKey("update") && parameters.get("update") != null) {
            final Update updates = (Update) parameters.get("update");
            int index = 0;
            for (UpdateSetOperation updateSetOperation : updates) {
                updateSetOperation.apply(sql, String.format("update[%d]", index++));
            }
        }


        sql.append("</set>");

        if (ids != null) {
            /**
             * <where>
             *     ${properties.idProperty.column}
             *     <foreach collection="ids" item="id" open=" IN (" separator="," close=")">
             *         #{id}
             *     </foreach>
             * </where>
             */
            sql.append(whereIdsNotNull());
        } else if (query instanceof Query) {
            ((Query) query).apply(sql, "query");
        }


    }


}

