package org.ifinal.finalframework.mybatis.sql.provider;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.ifinal.finalframework.annotation.data.LastModified;
import org.ifinal.finalframework.annotation.data.Metadata;
import org.ifinal.finalframework.annotation.data.Version;
import org.ifinal.finalframework.data.query.QEntity;
import org.ifinal.finalframework.data.query.QProperty;
import org.ifinal.finalframework.data.util.Velocities;
import org.ifinal.finalframework.mybatis.sql.AbsMapperSqlProvider;
import org.ifinal.finalframework.mybatis.sql.ScriptMapperHelper;
import org.ifinal.finalframework.util.Asserts;

/**
 * @author likly
 * @version 1.0.0
 * @see org.ifinal.finalframework.mybatis.mapper.AbsMapper#insert(String, Class, boolean, Collection)
 * @see org.ifinal.finalframework.mybatis.mapper.AbsMapper#replace(String, Class, Collection)
 * @see org.ifinal.finalframework.mybatis.mapper.AbsMapper#save(String, Class, Collection)
 * @since 1.0.0
 */
public class InsertSqlProvider implements AbsMapperSqlProvider, ScriptSqlProvider {

    private static final String METHOD_INSERT = "insert";

    private static final String METHOD_REPLACE = "replace";

    private static final String METHOD_SAVE = "save";

    private static final String INSERT_INTO = "INSERT INTO";

    private static final String INSERT_IGNORE_INTO = "INSERT IGNORE INTO";

    private static final String REPLACE_INTO = "REPLACE INTO";

    private static final String DEFAULT_WRITER = "#{${value}#if($javaType),javaType=$!{javaType.canonicalName}#end"
        + "#if($typeHandler),typeHandler=$!{typeHandler.canonicalName}#end}";

    private static final String TRIM_END = "</trim>";

    /**
     * @param context    context
     * @param parameters parameters
     * @return sql
     * @see org.ifinal.finalframework.mybatis.mapper.AbsMapper#insert(String, Class, boolean, Collection)
     */
    public String insert(final ProviderContext context, final Map<String, Object> parameters) {

        return provide(context, parameters);
    }

    /**
     * @param context    context
     * @param parameters parameters
     * @return sql
     * @see org.ifinal.finalframework.mybatis.mapper.AbsMapper#replace(String, Class, Collection)
     */
    public String replace(final ProviderContext context, final Map<String, Object> parameters) {

        return provide(context, parameters);
    }

    /**
     * @param context    context
     * @param parameters parameters
     * @return sql
     * @see org.ifinal.finalframework.mybatis.mapper.AbsMapper#save(String, Class, Collection)
     */
    public String save(final ProviderContext context, final Map<String, Object> parameters) {

        return provide(context, parameters);
    }

    @Override
    public void doProvide(final StringBuilder sql, final ProviderContext context,
        final Map<String, Object> parameters) {

        final String insertPrefix = getInsertPrefix(context.getMapperMethod(),
            parameters.containsKey("ignore") && Boolean.TRUE.equals(parameters.get("ignore")));

        final Class<?> view = (Class<?>) parameters.get("view");
        final QEntity<?, ?> entity = QEntity.from(getEntityClass(context.getMapperType()));
        parameters.put("entity", entity);

        appendInsertOrReplaceOrSave(sql, insertPrefix);
        appendColumns(sql, entity);
        appendValues(sql, entity);
        if (METHOD_SAVE.equals(context.getMapperMethod().getName())) {
            appendOnDuplicateKeyUpdate(sql, entity, view);
        }

    }

    private String getInsertPrefix(final Method method, final boolean ignore) {

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
    private void appendInsertOrReplaceOrSave(final StringBuilder sql, final String insertPrefix) {

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
     */
    private void appendColumns(final StringBuilder sql, final QEntity<?, ?> entity) {

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
     */
    private void appendValues(final StringBuilder sql, final QEntity<?, ?> entity) {

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

                    final String writer = Asserts.isBlank(property.getWriter()) ? DEFAULT_WRITER : property.getWriter();
                    value.append(
                        ScriptMapperHelper.cdata(Velocities.getValue(writer, buildPropertyMetadata(property)) + ","));

                    value
                        .append("</when>")
                        .append("<otherwise>null,</otherwise>")
                        .append("</choose>");
                    sql.append(value.toString());
                } else {
                    final StringBuilder value = new StringBuilder();

                    final String writer = Asserts.isBlank(property.getWriter()) ? DEFAULT_WRITER : property.getWriter();
                    value.append(
                        ScriptMapperHelper.cdata(Velocities.getValue(writer, buildPropertyMetadata(property)) + ","));
                    sql.append(value.toString());
                }

                sql.append("</if>");
            });

        sql.append(TRIM_END);

        sql.append("</foreach>");
    }

    private Metadata buildPropertyMetadata(final QProperty<?> property) {

        final Metadata metadata = new Metadata();
        metadata.setProperty(property.getName());
        metadata.setColumn(property.getColumn());
        metadata.setValue("item." + property.getPath());
        metadata.setJavaType(property.getType());
        metadata.setTypeHandler(property.getTypeHandler());
        return metadata;
    }

    /**
     * <trim prefix="ON DUPLICATE KEY UPDATE">
     * ${column} = value(${column}), version = version + 1, lastModified = NOW()
     * </trim>
     *
     * @param sql        sql
     * @param properties properties
     * @param view       view
     */
    private void appendOnDuplicateKeyUpdate(final StringBuilder sql, final QEntity<?, ?> properties,
        final Class<?> view) {

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

