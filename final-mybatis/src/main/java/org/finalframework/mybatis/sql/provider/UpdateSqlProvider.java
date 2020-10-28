package org.finalframework.mybatis.sql.provider;


import org.apache.ibatis.builder.annotation.ProviderContext;
import org.finalframework.annotation.IEntity;
import org.finalframework.annotation.IQuery;
import org.finalframework.annotation.data.Metadata;
import org.finalframework.data.query.QEntity;
import org.finalframework.data.query.Query;
import org.finalframework.data.query.Update;
import org.finalframework.data.query.UpdateSetOperation;
import org.finalframework.data.query.sql.AnnotationQueryProvider;
import org.finalframework.data.util.Velocities;
import org.finalframework.mybatis.sql.AbsMapperSqlProvider;
import org.finalframework.mybatis.sql.ScriptMapperHelper;
import org.finalframework.util.Asserts;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

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
 * @see org.finalframework.mybatis.mapper.AbsMapper#update(String, Class, IEntity, Update, boolean, Collection, IQuery)
 * @since 1.0
 */
public class UpdateSqlProvider implements AbsMapperSqlProvider, ScriptSqlProvider {
    private static final String DEFAULT_WRITER = "#{${value}#if($javaType),javaType=$!{javaType.canonicalName}#end#if($typeHandler),typeHandler=$!{typeHandler.canonicalName}#end}";

    private static final String PROPERTIES_PARAMETER_NAME = "properties";
    private static final String VIEW_PARAMETER_NAME = "view";
    private static final String SELECTIVE_PARAMETER_NAME = "selective";
    private static final String ENTITY_PARAMETER_NAME = "entity";
    private static final String UPDATE_PARAMETER_NAME = "update";
    private static final String IDS_PARAMETER_NAME = "ids";
    private static final String QUERY_PARAMETER_NAME = "query";

    /**
     * @see org.finalframework.mybatis.mapper.AbsMapper#update(String, Class, IEntity, Update, boolean, Collection, IQuery)
     */
    public String update(ProviderContext context, Map<String, Object> parameters) {
        return provide(context, parameters);
    }


    @Override
    public void doProvide(StringBuilder sql, ProviderContext context, Map<String, Object> parameters) {

        final Object query = parameters.get(QUERY_PARAMETER_NAME);
        final Class<?> view = (Class<?>) parameters.get(VIEW_PARAMETER_NAME);
        final boolean selective = Boolean.TRUE.equals(parameters.get(SELECTIVE_PARAMETER_NAME));

        Class<?> entity = getEntityClass(context.getMapperType());
        final QEntity<?, ?> properties = QEntity.from(entity);
        parameters.put(PROPERTIES_PARAMETER_NAME, properties);

        /*
         * <trim prefix="UPDATE">
         *      ${table}
         * </trim>
         */
        sql.append("<trim prefix=\"UPDATE\">").append("${table}").append("</trim>");

        sql.append("<set>");

        if (parameters.containsKey(ENTITY_PARAMETER_NAME) && parameters.get(ENTITY_PARAMETER_NAME) != null) {
            appendEntitySet(sql, properties, view, selective);
        } else if (parameters.containsKey(UPDATE_PARAMETER_NAME) && parameters.get(UPDATE_PARAMETER_NAME) != null) {
            final Update updates = (Update) parameters.get(UPDATE_PARAMETER_NAME);
            int index = 0;
            for (UpdateSetOperation updateSetOperation : updates) {
                updateSetOperation.apply(sql, String.format("update[%d]", index++));
            }
        }

        appendVersionProperty(sql, properties);

        sql.append("</set>");

        if (parameters.containsKey(IDS_PARAMETER_NAME) && parameters.get(IDS_PARAMETER_NAME) != null) {
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
        } else if (query != null) {
            sql.append(AnnotationQueryProvider.INSTANCE.provide("query", (Class<? extends IEntity<?>>) entity, query.getClass()));
        }


    }

    /**
     * <pre class="code">
     *      <set>
     *          <if test="properties.property.hasView(view)">
     *              <choose>
     *                  <when test="selective and entity.property != null">
     *                      ${properties.property.column} = #{entity.property}
     *                  </when>
     *                  <when test="!selective and entity.property != null">
     *                      ${properties.property.column} = #{entity.property}
     *                  </when>
     *                  <otherwise>${properties.property.column} = null,</otherwise>
     *              </choose>
     *          </if>
     *      </set>
     * </pre>
     *
     * @param sql       sql
     * @param entity    entity
     * @param selective selective
     */
    private void appendEntitySet(@NonNull StringBuilder sql, @NonNull QEntity<?, ?> entity, @Nullable Class<?> view, boolean selective) {
        entity.stream()
                .filter(property -> property.isWriteable() && property.isModifiable())
                .forEach(property -> {
                    // <if test="properties.property.hasView(view)>"
                    sql.append("<if test=\"properties.getRequiredProperty('")
                            .append(property.getPath())
                            .append("').hasView(view)\">");

                    final Metadata metadata = new Metadata();

                    metadata.setProperty(property.getName());
                    metadata.setColumn(property.getColumn());
                    metadata.setValue("entity." + property.getPath());
                    metadata.setJavaType(property.getType());
                    metadata.setTypeHandler(property.getTypeHandler());

                    final String writer = Asserts.isBlank(property.getWriter()) ? DEFAULT_WRITER : property.getWriter();
                    final String value = Velocities.getValue(writer, metadata);


                    // <choose>
                    sql.append("<choose>");

                    final String testWithSelective = ScriptMapperHelper.formatTest(ENTITY_PARAMETER_NAME, property.getPath(), true);

                    final String selectiveTest = testWithSelective == null ? "selective" : "selective and " + testWithSelective;

                    // <when test="selective and entity.path != null">
                    sql.append("<when test=\"").append(selectiveTest).append("\">")
                            // property.column = entity.path
                            .append(property.getColumn()).append(" = ").append(value).append(",")
                            // </when>
                            .append("</when>");

                    final String testNotWithSelective = ScriptMapperHelper.formatTest(ENTITY_PARAMETER_NAME, property.getPath(), false);
                    final String notSelectiveTest = testNotWithSelective == null ? "!selective" : "!selective and " + testNotWithSelective;


                    sql.append("<when test=\"").append(notSelectiveTest).append("\">")
                            .append(property.getColumn()).append(" = ").append(value).append(",")
                            .append("</when>");

                    if (testNotWithSelective != null) {
                        sql.append("<otherwise>")
                                .append(property.getColumn()).append(" = ").append("null").append(",")
                                .append("</otherwise>");
                    }


                    sql.append("</choose>");

                    sql.append("</if>");
                });
    }

    private void appendVersionProperty(StringBuilder sql, QEntity<?, ?> entity) {
        sql.append("<if test=\"properties.hasVersionProperty()\">");
        String version = entity.getVersionProperty().getColumn();
        sql.append(version)
                .append(" = ")
                .append(version)
                .append(" + 1,");

        sql.append("</if>");
    }


}

