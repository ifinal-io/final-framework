package org.finalframework.mybatis.builder;


import org.apache.ibatis.type.TypeHandler;
import org.finalframework.data.annotation.enums.ReferenceMode;
import org.finalframework.data.mapping.Entity;
import org.finalframework.data.mapping.Property;
import org.finalframework.data.mapping.converter.NameConverterRegistry;
import org.finalframework.data.mapping.generator.ColumnGenerator;
import org.finalframework.data.query.enums.UpdateOperation;
import org.finalframework.mybatis.Utils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-09 17:02:14
 * @since 1.0
 */
public class UpdateMethodXmlMapperBuilder extends AbsMethodXmlMapperBuilder implements MethodXmlMapperBuilder {

    private static final String SQL_UPDATE = "sql-update";

    @Override
    public boolean supports(Method method) {
        return !method.isDefault() && method.getName().equals("update");
    }

    @Override
    public Element build(Method method, Document document, Entity<?> entity) {
        /*
         *      <update id="update">
         *         UPDATE
         *         <include refid="sql-table"/>
         *         <include refid="sql-update"/>
         *         <where>
         *             <choose>
         *                 <when test="ids != null">
         *                     id IN
         *                     <foreach collection="ids" item="id" open="(" separator="," close=")">
         *                         #{id}
         *                     </foreach>
         *                 </when>
         *                 <when test="query != null">
         *                     #{query.sql}
         *                 </when>
         *             </choose>
         *         </where>
         *     </update>
         */
        final Element update = document.createElement("update");
        update.setAttribute("id", method.getName());
        update.appendChild(textNode(document, "\n\t\tUPDATE\n"));
        update.appendChild(include(document, SQL_TABLE));
        update.appendChild(include(document, SQL_UPDATE));
        update.appendChild(where(document, whenIdsNotNull(document, entity), whenQueryNotNull(document)));
        update.appendChild(include(document, SQL_ORDER));
        update.appendChild(include(document, SQL_LIMIT));
        return update;
    }

    @Override
    public Collection<Element> sqlFragments(Method method, Document document, Entity<?> entity) {
        return Collections.singletonList(appendUpdateSqlFragment(document, entity));
    }

    @SuppressWarnings("all")
    private Element appendUpdateSqlFragment(@NonNull Document document, @NonNull Entity<?> entity) {
        /*
         *      <sql id="sql-update">
         *         <set>
         *             <choose>
         *                 <when test="entity != null and selective == true">
         *                     <if test="entity.property != null">
         *                         column = #{entity.property,javaType=,typeHandler},
         *                     </if>
         *                     <if test="entity.property != null and entity.property.property != null">
         *                         column = #{entity.property.property,javaType=,typeHandler},
         *                     </if>
         *                 </when>
         *                 <when test="entity != null">
         *                     column = #{entity.property,javaType=,typeHandler},
         *                     <if test="entity.property != null">
         *                         column = #{entity.property.property,javaType=,typeHandler},
         *                     </if>
         *                 </when>
         *                 <when test="update != null">
         *                     <if test="update.contains('property')">
         *                         <choose>
         *                             <when test="update.getUpdateSet('property').action.name() == 'EQUAL'">
         *                                 column = #{update.getUpdateSet('property').value,javaType=,typeHandler=},
         *                             </when>
         *                             <when test="update.getUpdateSet('property').action.name() == 'INC'">
         *                                 column = column + 1,
         *                             </when>
         *                             <when test="update.getUpdateSet('property').action.name() == 'INCR'">
         *                                 column = column + #{update.getUpdateSet('property').value},
         *                             </when>
         *                             <when test="update.getUpdateSet('property').action.name() == 'DEC'">
         *                                 column = column - 1,
         *                             </when>
         *                             <when test="update.getUpdateSet('property').action.name() == 'DECR'">
         *                                 column = column - #{update.getUpdateSet('property').value},
         *                             </when>
         *                         </choose>
         *                     </if>
         *                     <if test="update.contains('property') and update.getUpdateSet('property').value.property != null">
         *                          column = #{update.getUpdateSet('property').value.property,javaType=,typeHandler=},
         *                     </if>
         *                 </when>
         *             </choose>
         *         </set>
         *     </sql>
         */
        //<sql id="update">
        final Element sql = document.createElement("sql");
        sql.setAttribute("id", SQL_UPDATE);
        //<set>
        final Element set = document.createElement("set");
        //      <choose>
        //          <when test="entity != null">
        //          <when test="entity != null and selective == true">
        //          <when test="update != null">
        //      </choose>
        set.appendChild(choose(document, Arrays.asList(
                whenEntityNotNull(document, entity, true),
                whenEntityNotNull(document, entity, false),
                whenUpdateNotNull(document, entity)
        )));
        //</set>
        sql.appendChild(set);
        //</sql>
        return sql;
    }

    private Element whenEntityNotNull(@NonNull Document document, @NonNull Entity<?> entity, boolean selective) {
        //        <when test="entity != null and selective == true">
        //              <choose>
        //                  <when test="view != null and view == 'view'.toString()">
        //                  </when>
        //              </choose>
        //          </when>
        final String test = selective ? "entity != null and selective == true" : "entity != null";

        final List<Element> views = entity.getViews().stream().map(view -> buildEntitySelectiveViewElement(document, entity, selective, view)).collect(Collectors.toList());
        views.add(buildEntitySelectiveViewElement(document, entity, selective, null));
        final Element choose = choose(document, views);

        return whenOrOtherwise(document, test, choose);
    }

    private Element buildEntitySelectiveViewElement(@NonNull Document document, @NonNull Entity<?> entity, boolean selective, @Nullable Class<?> view) {

        final Element whenOrOtherwise = document.createElement(view != null ? "when" : "otherwise");

        if (view != null) {
            final String test = String.format("view != null and view.getCanonicalName() == '%s'.toString()", view.getCanonicalName());
            whenOrOtherwise.setAttribute("test", test);
        }

        entity.stream().filter(it -> !it.isTransient() && it.updatable() && (view == null || it.hasView(view)))
                .forEach(property -> {
                    if (property.isReference()) {
                        /**
                         * <if test="entity.property != null and entity.property.property != null">
                         *     column = #{entity.property.property,javaType=,typeHandler},
                         * </if>
                         */
                        final Class multiType = Utils.getPropertyJavaType(property);
                        final Entity<?> multiEntity = Entity.from(multiType);
                        property.referenceProperties().stream()
                                .map(multiEntity::getRequiredPersistentProperty)
                                .map(multiProperty -> {

                                    ColumnGenerator columnGenerator = Utils.getPropertyColumnGenerator(multiProperty);
                                    final String column = columnGenerator.generateWriteColumn(property.getTable(), property, multiProperty);
                                    final String value = columnGenerator.generateWriteValue(property, multiProperty, "entity");

                                    final Element ifPropertyNotNull = document.createElement("if");
                                    final String ifTest = selective ? String.format("entity.%s != null and entity.%s.%s != null", property.getName(), property.getName(), multiProperty.getName())
                                            : String.format("entity.%s != null", property.getName());
                                    ifPropertyNotNull.setAttribute("test", ifTest);
                                    ifPropertyNotNull.appendChild(textNode(document, String.format("%s = %s,", column, value)));
                                    return ifPropertyNotNull;
                                }).forEach(whenOrOtherwise::appendChild);
                    } else {
                        /**
                         * <if test="entity.property != null">
                         *     column = #{entity.property,javaType=,typeHandler=}
                         * </if>
                         */

                        ColumnGenerator columnGenerator = Utils.getPropertyColumnGenerator(property);
                        final String column = columnGenerator.generateWriteColumn(property.getTable(), null, property);
                        final String value = columnGenerator.generateWriteValue(null, property, "entity");

                        if (selective) {
                            final Element ifPropertyNotNull = document.createElement("if");
                            final String ifTest = String.format("entity.%s != null", property.getName());
                            ifPropertyNotNull.setAttribute("test", ifTest);
                            ifPropertyNotNull.appendChild(textNode(document, String.format("%s = %s,", column, value)));
                            whenOrOtherwise.appendChild(ifPropertyNotNull);
                        } else {
                            whenOrOtherwise.appendChild(textNode(document, String.format("%s = %s,", column, value)));
                        }
                    }

                });

        return whenOrOtherwise;
    }

    private Element whenUpdateNotNull(@NonNull Document document, @NonNull Entity<?> entity) {
        //        <when test="update != null">
        final Element whenUpdateNotNull = document.createElement("when");
        whenUpdateNotNull.setAttribute("test", "update != null");

        entity.stream().filter(Property::updatable)
                .forEach(property -> {

                    /*
                     * <if test="update.contains('property') and update['property'].value.property != null">
                     *      column = #{update[property].value.property,javaType=,typeHandler=}
                     * </if>
                     */
                    if (property.isReference()) {
                        final Class multiType = Utils.getPropertyJavaType(property);
                        final Entity<?> multiEntity = Entity.from(multiType);
                        property.referenceProperties().stream()
                                .map(multiEntity::getRequiredPersistentProperty)
                                .map(multiProperty -> {
                                    final Class javaType = Utils.getPropertyJavaType(multiProperty);
                                    final TypeHandler typeHandler = Utils.getPropertyTypeHandler(multiProperty);

                                    final Element ifUpdateContains = document.createElement("if");

                                    final String referenceColumn = property.referenceColumn(multiProperty.getName()) != null
                                            ? property.referenceColumn(multiProperty.getName())
                                            : multiProperty.getColumn();

                                    final String multiColumn = multiProperty.isIdProperty() && multiProperty.referenceMode() == ReferenceMode.SIMPLE ? property.getColumn()
                                            : property.getColumn() + referenceColumn.substring(0, 1).toUpperCase() + referenceColumn.substring(1);
                                    final String updatePath = multiColumn;
                                    final String updateColumn = NameConverterRegistry.getInstance().getColumnNameConverter().convert(multiColumn);
                                    final String ifTest = String.format("update['%s'] != null", updatePath);
                                    ifUpdateContains.setAttribute("test", ifTest);

                                    List<Element> whenElements = Arrays.stream(UpdateOperation.values())
                                            .map(operation -> {
                                                final String whenTest = String.format("update['%s'].action.name() == '%s'", updatePath, operation.name());
                                                String updateSql = null;
                                                switch (operation) {
                                                    case EQUAL:
                                                        updateSql = typeHandler == null ?
                                                                String.format("%s = #{update[%s].value},", updateColumn, updatePath)
                                                                : String.format("%s = #{update[%s].value,javaType=%s,typeHandler=%s},",
                                                                updateColumn, updatePath, javaType.getCanonicalName(), typeHandler.getClass().getCanonicalName());
                                                        break;
                                                    case INC:
                                                        updateSql = String.format("%s = %s + 1,", updateColumn, updateColumn);
                                                        break;
                                                    case INCR:
                                                        updateSql = String.format("%s = %s + #{update[%s].value},", updateColumn, updateColumn, updatePath);
                                                        break;
                                                    case DEC:
                                                        updateSql = String.format("%s = %s - 1,", updateColumn, updateColumn);
                                                        break;
                                                    case DECR:
                                                        updateSql = String.format("%s = %s - #{update[%s].value},", updateColumn, updateColumn, updatePath);
                                                        break;
                                                }
                                                return whenOrOtherwise(document, whenTest, textNode(document, updateSql));
                                            }).collect(Collectors.toList());
                                    ifUpdateContains.appendChild(choose(document, whenElements));
                                    return ifUpdateContains;
                                }).forEach(whenUpdateNotNull::appendChild);

                    } else {
                        final Class javaType = Utils.getPropertyJavaType(property);
                        final TypeHandler typeHandler = Utils.getPropertyTypeHandler(property);
                        final Element ifUpdateContains = document.createElement("if");
                        final String updatePath = property.getName();
                        final String ifTest = String.format("update['%s'] != null", updatePath);
                        ifUpdateContains.setAttribute("test", ifTest);

                        ifUpdateContains.setAttribute("test", ifTest);
                        final String multiColumn = property.getColumn();

                        List<Element> whenElements = Arrays.stream(UpdateOperation.values())
                                .map(operation -> {
                                    final String whenTest = String.format("update['%s'].operation.name() == '%s'", updatePath, operation.name());
                                    String updateSql = null;
                                    switch (operation) {
                                        case EQUAL:
                                            updateSql = typeHandler == null ?
                                                    String.format("%s = #{update[%s].value},", multiColumn, updatePath)
                                                    : String.format("%s = #{update[%s].value,javaType=%s,typeHandler=%s},",
                                                    multiColumn, updatePath, javaType.getCanonicalName(), typeHandler.getClass().getCanonicalName());
                                            break;
                                        case INC:
                                            updateSql = String.format("%s = %s + 1,", multiColumn, multiColumn);
                                            break;
                                        case INCR:
                                            updateSql = String.format("%s = %s + #{update[%s].value},", multiColumn, multiColumn, updatePath);
                                            break;
                                        case DEC:
                                            updateSql = String.format("%s = %s - 1,", multiColumn, multiColumn);
                                            break;
                                        case DECR:
                                            updateSql = String.format("%s = %s - #{update[%s].value},", multiColumn, multiColumn, updatePath);
                                            break;
                                    }
                                    return whenOrOtherwise(document, whenTest, textNode(document, updateSql));
                                }).collect(Collectors.toList());
                        ifUpdateContains.appendChild(choose(document, whenElements));

                        whenUpdateNotNull.appendChild(ifUpdateContains);
                    }
                });
        //        </when>
        return whenUpdateNotNull;
    }
}
