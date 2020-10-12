package org.finalframework.mybatis.sql.provider;


import org.apache.ibatis.builder.annotation.ProviderContext;
import org.finalframework.annotation.data.LastModified;
import org.finalframework.annotation.data.Metadata;
import org.finalframework.annotation.data.Version;
import org.finalframework.core.Asserts;
import org.finalframework.data.query.QEntity;
import org.finalframework.data.query.QProperty;
import org.finalframework.data.util.Velocities;
import org.finalframework.mybatis.sql.AbsMapperSqlProvider;
import org.finalframework.mybatis.sql.ScriptMapperHelper;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <pre class="code">
 *     <code>
 *         <script>
 *             <trim prefix="INSERT INFO | INSERT IGNORE INTO | REPLACE INTO">
 *                 ${table}
 *             </trim>
 *             <trim prefix="(" suffix=")" suffixOverrides=",">
 *                  <if test="entity.property.hasView(view)">
 *                      entity.property.column,
 *                  </if>
 *             </trim>
 *             <foreach collection="list" item="entity" open="VALUES" separator=",">
 *                  <trim prefix="(" suffix=")" suffixOverrides=",">
 *                      <if test="entity.property.hasView(view)">
 *                          ${property.path,javaType=,typeHandler=},
 *                      </if>
 *                  </trim>
 *             </foreach>
 *             <trim prefix="ON DUPLICATE KEY UPDATE">
 *                  column = values(column),
 *                  version = version + 1,
 *                  last_modified = NOW()
 *             </trim>
 *         </script>
 *     </code>
 * </pre>
 *
 * @author likly
 * @version 1.0
 * @date 2020-04-15 00:23:52
 * @see org.finalframework.mybatis.mapper.AbsMapper#insert(String, Class, boolean, Collection)
 * @see org.finalframework.mybatis.mapper.AbsMapper#replace(String, Class, Collection)
 * @see org.finalframework.mybatis.mapper.AbsMapper#save(String, Class, Collection)
 * @since 1.0
 */
public class InsertSqlProvider implements AbsMapperSqlProvider, ScriptSqlProvider {

    private static final String METHOD_INSERT = "insert";
    private static final String METHOD_REPLACE = "replace";
    private static final String METHOD_SAVE = "save";

    private static final String INSERT_INTO = "INSERT INTO";
    private static final String INSERT_IGNORE_INTO = "INSERT IGNORE INTO";
    private static final String REPLACE_INTO = "REPLACE INTO";

    private static final String DEFAULT_WRITER = "#{${value}#if($javaType),javaType=$!{javaType.canonicalName}#end#if($typeHandler),typeHandler=$!{typeHandler.canonicalName}#end}";

    private static final String TRIM_END = "</trim>";


    /**
     * @see org.finalframework.mybatis.mapper.AbsMapper#insert(String, Class, boolean, Collection)
     */
    public String insert(ProviderContext context, Map<String, Object> parameters) {
        return provide(context, parameters);
    }

    /**
     * @see org.finalframework.mybatis.mapper.AbsMapper#replace(String, Class, Collection)
     */
    public String replace(ProviderContext context, Map<String, Object> parameters) {
        return provide(context, parameters);
    }

    /**
     * @see org.finalframework.mybatis.mapper.AbsMapper#save(String, Class, Collection)
     */
    public String save(ProviderContext context, Map<String, Object> parameters) {
        return provide(context, parameters);
    }


    @Override
    public void doProvide(StringBuilder sql, ProviderContext context, Map<String, Object> parameters) {

        final String insertPrefix = getInsertPrefix(context.getMapperMethod(),
                parameters.containsKey("ignore") && Boolean.TRUE.equals(parameters.get("ignore")));

        final Class<?> view = (Class<?>) parameters.get("view");
        final QEntity<?, ?> entity = QEntity.from(getEntityClass(context.getMapperType()));
        parameters.put("entity", entity);


        appendInsertOrReplaceOrSave(sql, insertPrefix);
        appendColumns(sql, entity, view);
        appendValues(sql, entity, view);
        if (METHOD_SAVE.equals(context.getMapperMethod().getName())) {
            appendOnDuplicateKeyUpdate(sql, entity, view);
        }

    }


    private String getInsertPrefix(Method method, boolean ignore) {
        switch (method.getName()) {
            case METHOD_INSERT:
                return ignore ? INSERT_IGNORE_INTO : INSERT_INTO;
            case METHOD_REPLACE:
                return REPLACE_INTO;
            case METHOD_SAVE:
                return INSERT_INTO;
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * <trim prefix="INSERT INTO | INSERT IGNORE INTO | REPLACE INTO">
     * ${table}
     * </trim>
     */
    private void appendInsertOrReplaceOrSave(StringBuilder sql, String insertPrefix) {
        sql.append("<trim prefix=\"").append(insertPrefix).append("\">")
                .append(ScriptMapperHelper.table())
                .append(TRIM_END);
    }

    /**
     * <pre>
     *     <code>
     *         <trim prefix="(" suffix=")">
     *              ${columns}
     *         </trim>
     *     </code>
     * </pre>
     *
     * @param sql    sql
     * @param entity entity
     * @param view   view
     */
    private void appendColumns(StringBuilder sql, QEntity<?, ?> entity, Class<?> view) {

        // <trim prefix="(" suffix=)"" suffixOverrides=",">
        sql.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");

        // <if test="entity.property.hasView(view)">
        //      entity.property.column,
        // </if>
        entity.stream()
                .filter(QProperty::isWriteable)
                .forEach(property ->
                        sql.append("<if test=\"entity.getRequiredProperty('")
                                .append(property.getPath())
                                .append("').hasView(view)\">")
                                .append(property.getColumn())
                                .append(",</if>"));
        // </trim>
        sql.append(TRIM_END);
    }

    /**
     * <pre>
     *     <code>
     *         <foreach collection="list" item="entity" open="VALUES" separator=",">
     *              <trim prefix="(" suffix=")" suffixOverrides=",">
     *                  values
     *              </trim>
     *         </foreach>
     *     </code>
     * </pre>
     *
     * @param sql    sql
     * @param entity entity
     * @param view   view
     */
    private void appendValues(StringBuilder sql, QEntity<?, ?> entity, Class<?> view) {
        sql.append("<foreach collection=\"list\" item=\"item\" open=\"VALUES\" separator=\",\">");

        sql.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");

        entity.stream()
                .filter(QProperty::isWriteable)
                .forEach(property -> {
                    sql.append("<if test=\"entity.getRequiredProperty('")
                            .append(property.getPath())
                            .append("').hasView(view)\">");

                    if (property.getPath().contains(".")) {
                        final StringBuilder value = new StringBuilder();
                        value
                                .append("<choose>")
                                .append("<when test=\"")
                                .append(ScriptMapperHelper.formatTest("item", property.getPath(), false))
                                .append("\">");

                        final Metadata metadata = new Metadata();
                        metadata.setProperty(property.getName());
                        metadata.setColumn(property.getColumn());
                        metadata.setValue("item." + property.getPath());
                        metadata.setJavaType(property.getType());
                        metadata.setTypeHandler(property.getTypeHandler());

                        final String writer = Asserts.isBlank(property.getWriter()) ? DEFAULT_WRITER : property.getWriter();
                        value.append(ScriptMapperHelper.cdata(Velocities.getValue(writer, metadata) + ","));

                        value
                                .append("</when>")
                                .append("<otherwise>null,</otherwise>")
                                .append("</choose>");
                        sql.append(value.toString());
                    } else {
                        final StringBuilder value = new StringBuilder();
                        final Metadata metadata = new Metadata();
                        metadata.setProperty(property.getName());
                        metadata.setColumn(property.getColumn());
                        metadata.setValue("item." + property.getPath());
                        metadata.setJavaType(property.getType());
                        metadata.setTypeHandler(property.getTypeHandler());

                        final String writer = Asserts.isBlank(property.getWriter()) ? DEFAULT_WRITER : property.getWriter();
                        value.append(ScriptMapperHelper.cdata(Velocities.getValue(writer, metadata) + ","));
                        sql.append(value.toString());
                    }


                    sql.append("</if>");
                });


        sql.append(TRIM_END);

        sql.append("</foreach>");
    }


    /**
     * <trim prefix="ON DUPLICATE KEY UPDATE">
     * ${column} = value(${column}),
     * version = version + 1,
     * lastModified = NOW()
     * </trim>
     *
     * @param sql        sql
     * @param properties properties
     * @param view       view
     */
    private void appendOnDuplicateKeyUpdate(StringBuilder sql, QEntity<?, ?> properties, Class<?> view) {
        final String onDuplicateKeyUpdate = properties.stream()
                .filter(property -> (property.isWriteable() && property.hasView(view))
                        || property.getProperty().isAnnotationPresent(Version.class)
                        || property.getProperty().isAnnotationPresent(LastModified.class))
                .map(property -> {
                    String column = property.getColumn();
                    if (property.getProperty().isAnnotationPresent(Version.class)) {
                        return String.format("%s = %s + 1", column, column);
                    } else if (property.getProperty().isAnnotationPresent(LastModified.class)) {
                        return String.format("%s = NOW()", column);
                    } else {
                        return String.format("%s = values(%s)", column, column);
                    }
                }).collect(Collectors.joining(","));

        sql.append("<trim prefix=\"ON DUPLICATE KEY UPDATE\">");

        sql.append(ScriptMapperHelper.cdata(onDuplicateKeyUpdate));

        sql.append(TRIM_END);
    }


}

