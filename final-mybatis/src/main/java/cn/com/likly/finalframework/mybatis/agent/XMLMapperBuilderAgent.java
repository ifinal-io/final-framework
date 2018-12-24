package cn.com.likly.finalframework.mybatis.agent;

import cn.com.likly.finalframework.data.annotation.MultiColumn;
import cn.com.likly.finalframework.data.annotation.enums.PrimaryKeyType;
import cn.com.likly.finalframework.data.mapping.Entity;
import cn.com.likly.finalframework.data.mapping.Property;
import cn.com.likly.finalframework.mybatis.EntityHolderCache;
import cn.com.likly.finalframework.mybatis.handler.DefaultTypeHandlerRegistry;
import cn.com.likly.finalframework.mybatis.handler.TypeHandlerRegistry;
import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.type.TypeHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-21 23:40:31
 * @since 1.0
 */
public class XMLMapperBuilderAgent {

    private static final TypeHandlerRegistry typeHandlerRegistry = new DefaultTypeHandlerRegistry();
    private static final EntityHolderCache cache = new EntityHolderCache();
    private static final String SQL_TABLE = "sql_table";
    private static final String SQL_UPDATE = "sql_update";
    private static final String SQL_SELECT = "sql_select";

    private final XNode context;
    private final Document document;
    private final Entity<Property> entity;
    private final Class type;

    public XMLMapperBuilderAgent(XNode context, Entity<Property> entity) {
        this.context = context;
        this.document = context.getNode().getOwnerDocument();
        this.entity = entity;
        this.type = entity.getType();
        init();
    }

    @SuppressWarnings("unused")
    public static void configurationDefaultElement(XNode context) {
        String namespace = context.getStringAttribute("namespace");
        if (namespace == null || namespace.equals("")) {
            throw new BuilderException("Mapper's namespace cannot be empty");
        }
        try {
            Class mapperClass = Class.forName(namespace);
            Entity entity = cache.get(mapperClass);
            final XMLMapperBuilderAgent agent = new XMLMapperBuilderAgent(context, entity);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Mapper cannot be found:mapper=" + namespace);
        }
    }

    private static Class getPropertyJavaType(Property property) {
        return property.isCollectionLike() ? property.getComponentType() : property.getType();
    }

    private static TypeHandler getPropertyTypeHandler(Property property) {
        final Class javaType = property.isCollectionLike() ? property.getComponentType() : property.getType();
        final Class collectionType = property.isCollectionLike() ? property.getType() : null;
        return typeHandlerRegistry.getTypeHandler(javaType, collectionType, property.getPersistentType());
    }

    private void init() {
        appendResultMapElement();
        //mapper method
        appendInsertElement();
        appendUpdateElement();
        appendDeleteElement();
        appendSelectElement();
        appendSelectOneElement();
        appendSelectCountElement();
        //sql fragment
        //sql-table
        appendSqlTableFragment();
        appendSqlUpdateFragment();
        appendSqlSelectFragment();

        String result = null;

        if (document != null) {
            StringWriter strWtr = new StringWriter();
            StreamResult strResult = new StreamResult(strWtr);
            TransformerFactory tfac = TransformerFactory.newInstance();
            try {
                javax.xml.transform.Transformer t = tfac.newTransformer();
                t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                t.setOutputProperty(OutputKeys.INDENT, "yes");
                t.setOutputProperty(OutputKeys.METHOD, "xml"); // xml, html,
                // text
                t.setOutputProperty(
                        "{http://xml.apache.org/xslt}indent-amount", "4");
                t.transform(new DOMSource(document.getDocumentElement()),
                        strResult);
            } catch (Exception e) {
                System.err.println("XML.toString(Document): " + e);
            }
            result = strResult.getWriter().toString();
            try {
                strWtr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println(result);
    }

    /*
     * <resultMap id="****Map" type="">
     *     <id property="" column="" javaType="" jdbcType="" typeHandler=""/>
     *     <result property="" column="" javaType="" jdbcType="" typeHandler=""/>
     *     <result property="" column="" javaType="" jdbcType="" typeHandler=""/>
     *     <association property="" javaType="">
     *          <id property="" column="" javaType="" jdbcType="" typeHandler=""/>
     *          <result property="" column="" javaType="" jdbcType="" typeHandler=""/>
     *     </association>
     * </resultMap>
     */
    private void appendResultMapElement() {
        final Element resultMap = document.createElement("resultMap");
        resultMap.setAttribute("id", type.getSimpleName() + "Map");
        resultMap.setAttribute("type", type.getCanonicalName());

        entity.stream().filter(it -> !it.isTransient() && !it.hasAnnotation(MultiColumn.class))
                .map(this::buildResultElement)
                .forEach(resultMap::appendChild);
        entity.stream().filter(it -> !it.isTransient() && it.hasAnnotation(MultiColumn.class))
                .map(this::buildAssociationElement)
                .forEach(resultMap::appendChild);
        context.getNode().appendChild(resultMap);
    }

    //    ******************************************************************************************************************

    private Element buildAssociationElement(Property property) {
        final Class javaType = getPropertyJavaType(property);
        final Element association = document.createElement("association");
        association.setAttribute("property", property.getName());
        association.setAttribute("javaType", javaType.getCanonicalName());
        MultiColumn multiColumn = property.findAnnotation(MultiColumn.class);
        Entity<Property> multiEntity = Entity.from(javaType);
        Arrays.stream(multiColumn.properties())
                .map(multiEntity::getRequiredPersistentProperty)
                .map(this::buildResultElement)
                .forEach(association::appendChild);
        return association;
    }

    private Element buildResultElement(Property property) {
        final Class javaType = getPropertyJavaType(property);
        final Class collectionType = property.isCollectionLike() ? property.getType() : null;
        TypeHandler typeHandler = typeHandlerRegistry.getTypeHandler(javaType, collectionType, property.getPersistentType());
        final String name = property.isIdProperty() ? "id" : "result";
        final Element result = document.createElement(name);
        result.setAttribute("property", property.getName());
        result.setAttribute("column", property.getColumn());
        if (javaType != null) {
            result.setAttribute("javaType", javaType.getCanonicalName());
        }
        if (typeHandler != null) {
            result.setAttribute("typeHandler", typeHandler.getClass().getCanonicalName());
        }
        return result;
    }

    private void appendSqlTableFragment() {
        /*
         *     <sql id="sql-table">
         *         <choose>
         *             <when test="tableName != null">
         *                 ${tableName}
         *             </when>
         *             <otherwise>
         *                 tableName
         *             </otherwise>
         *         </choose>
         *     </sql>
         */
        //<sql id="sql-table">
        final Element sqlTableFragment = document.createElement("sql");
        sqlTableFragment.setAttribute("id", SQL_TABLE);
        //      <choose>
        final Element choose = document.createElement("choose");
        //              <when test="tableName != null">
        final Element whenTableNameNotNull = document.createElement("when");
        whenTableNameNotNull.setAttribute("test", "tableName != null");
        //                  ${tableName}
        whenTableNameNotNull.appendChild(textNote("${tableName}"));
        //              </when>
        choose.appendChild(whenTableNameNotNull);
        //              <otherwise>
        final Element otherwise = document.createElement("otherwise");
        //                  tableName
        otherwise.appendChild(textNote(entity.getTable()));
        //              </otherwise>
        choose.appendChild(otherwise);
        //      </choose>
        sqlTableFragment.appendChild(choose);
        //</sql>
        context.getNode().appendChild(sqlTableFragment);
    }

    @SuppressWarnings("all")
    private void appendSqlUpdateFragment() {
        /*
         *      <sql id="sql-update">
         *         <set>
         *             <choose>
         *                 <when test="entity != null">
         *                     <if test="entity.property != null">
         *                         column = #{entity.property,javaType=,typeHandler}
         *                     </if>
         *                     <if test="entity.property != null and entity.property.property != null">
         *                         column = #{entity.property.property,javaType=,typeHandler}
         *                     </if>
         *                 </when>
         *                 <when test="update != null">
         *                     <if test="update.contains('property')">
         *                         <choose>
         *                             <when test="update.getUpdateSet('property').operation.name() == 'EQUAL'">
         *                                 column = #{update.getUpdateSet('property').value,javaType=,typeHandler=},
         *                             </when>
         *                             <when test="update.getUpdateSet('property').operation.name() == 'INC'">
         *                                 column = column + 1,
         *                             </when>
         *                             <when test="update.getUpdateSet('property').operation.name() == 'INCR'">
         *                                 column = column + #{update.getUpdateSet('property').value},
         *                             </when>
         *                             <when test="update.getUpdateSet('property').operation.name() == 'DEC'">
         *                                 column = column - 1,
         *                             </when>
         *                             <when test="update.getUpdateSet('property').operation.name() == 'DECR'">
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
        //    <choose>
        final Element choose = document.createElement("choose");
        //        <when test="entity != null">
        final Element whenEntityNotNull = document.createElement("when");
        whenEntityNotNull.setAttribute("test", "entity != null");
        entity.stream().filter(Property::updatable)
                .forEach(property -> {
                    if (property.hasAnnotation(MultiColumn.class)) {
                        /**
                         * <if test="entity.property != null and entity.property.property != null">
                         *     column = #{entity.property.property,javaType=,typeHandler},
                         * </if>
                         */
                        final Class multiType = getPropertyJavaType(property);
                        final Entity<Property> multiEntity = Entity.from(multiType);
                        Arrays.stream(property.findAnnotation(MultiColumn.class).properties())
                                .map(multiEntity::getRequiredPersistentProperty)
                                .map(multiProperty -> {

                                    final Class javaType = getPropertyJavaType(multiProperty);
                                    final TypeHandler typeHandler = getPropertyTypeHandler(multiProperty);

                                    final Element ifPropertyNotNull = document.createElement("if");
                                    final String ifTest = String.format("entity.%s != null and entity.%s.%s != null", property.getName(), property.getName(), multiProperty.getName());
                                    ifPropertyNotNull.setAttribute("test", ifTest);
                                    final String multiColumn = property.getName() + multiProperty.getColumn().substring(0, 1).toUpperCase() + multiProperty.getColumn().substring(1);
                                    final StringBuilder builder = new StringBuilder();
                                    builder.append(multiColumn)
                                            .append(" = ")
                                            .append("#{entity.")
                                            .append(property.getName()).append(".").append(multiProperty.getName());
                                    if (typeHandler != null) {
                                        builder.append(",javaType=").append(javaType.getCanonicalName())
                                                .append(",typeHandler=").append(typeHandler.getClass().getCanonicalName());
                                    }
                                    builder.append("},");
                                    ifPropertyNotNull.appendChild(textNote(builder.toString()));
                                    return ifPropertyNotNull;
                                }).forEach(whenEntityNotNull::appendChild);
                    } else {
                        /**
                         * <if test="entity.property != null">
                         *     column = #{entity.property,javaType=,typeHandler=}
                         * </if>
                         */
                        final Class javaType = getPropertyJavaType(property);
                        final TypeHandler typeHandler = getPropertyTypeHandler(property);
                        final StringBuilder builder = new StringBuilder();
                        builder.append(property.getColumn())
                                .append(" = ")
                                .append("#{entity.")
                                .append(property.getName());

                        if (typeHandler != null) {
                            builder.append(",javaType=").append(javaType.getCanonicalName())
                                    .append(",typeHandler=").append(typeHandler.getClass().getCanonicalName());
                        }
                        builder.append("},");

                        final Element ifPropertyNotNull = document.createElement("if");
                        final String ifTest = String.format("entity.%s != null", property.getName());
                        ifPropertyNotNull.setAttribute("test", ifTest);
                        ifPropertyNotNull.appendChild(textNote(builder.toString()));
                        whenEntityNotNull.appendChild(ifPropertyNotNull);
                    }

                });
        choose.appendChild(whenEntityNotNull);
        //        <when test="update != null">
        final Element whenUpdateNotNull = document.createElement("when");
        whenUpdateNotNull.setAttribute("test", "update != null");

        entity.stream().filter(Property::updatable)
                .forEach(property -> {


                    /*
                     * <if test="update.contains('property') and update.getUpdateSet('property').value.property != null">
                     *      column = #{update.getUpdateSet('property').value.property,javaType=,typeHandler=}
                     * </if>
                     */
                    if (property.hasAnnotation(MultiColumn.class)) {
                        final Class multiType = getPropertyJavaType(property);
                        final Entity<Property> multiEntity = Entity.from(multiType);
                        Arrays.stream(property.findAnnotation(MultiColumn.class)
                                .properties())
                                .map(multiEntity::getRequiredPersistentProperty)
                                .map(multiProperty -> {

                                    final Class javaType = getPropertyJavaType(multiProperty);
                                    final TypeHandler typeHandler = getPropertyTypeHandler(multiProperty);

                                    final Element ifUpdateContains = document.createElement("if");
                                    final String ifTest = String.format("update.contains('%s') and update.getUpdateSet('%s').value.%s != null",
                                            property.getName(), property.getName(), multiProperty.getName());
                                    ifUpdateContains.setAttribute("test", ifTest);
                                    final String multiColumn = property.getName() + multiProperty.getColumn().substring(0, 1).toUpperCase() + multiProperty.getColumn().substring(1);
                                    final StringBuilder builder = new StringBuilder();
                                    builder.append(multiColumn)
                                            .append(" = ")
                                            .append("#{")
                                            .append("update.getUpdateSet('")
                                            .append(property.getName()).append(".").append(multiProperty.getName())
                                            .append("')");
                                    if (!multiProperty.isEnum() && typeHandler != null) {
                                        builder.append(",javaType=").append(javaType.getCanonicalName())
                                                .append(",typeHandler=").append(typeHandler.getClass().getCanonicalName());
                                    }
                                    builder.append("},");
                                    ifUpdateContains.appendChild(textNote(builder.toString()));
                                    return ifUpdateContains;
                                }).forEach(whenUpdateNotNull::appendChild);

                    } else {
                        final Class javaType = getPropertyJavaType(property);
                        final TypeHandler typeHandler = getPropertyTypeHandler(property);
                        final Element ifUpdateContains = document.createElement("if");
                        ifUpdateContains.setAttribute("test", "update.contains('" + property.getName() + "')");

                        final Element updateSetChoose = document.createElement("choose");

                        // <when test="update.getUpdateSet('property').operation.name() == 'EQUAL'">
                        //       column = #{update.getUpdateSet('property').value,javaType=,typeHandler=}
                        // </when>
                        final Element whenSetOperationEqual = document.createElement("when");
                        whenSetOperationEqual.setAttribute("test", "update.getUpdateSet('" + property.getName() + "').operation.name() == 'EQUAL'");
                        final StringBuilder builder = new StringBuilder();
                        builder.append(property.getColumn())
                                .append(" = ")
                                .append("#{")
                                .append("update.getUpdateSet('")
                                .append(property.getName())
                                .append("')");
                        if (typeHandler != null) {
                            builder.append(",javaType=").append(javaType.getCanonicalName())
                                    .append(",typeHandler=").append(typeHandler.getClass().getCanonicalName());
                        }
                        builder.append("},");
                        whenSetOperationEqual.appendChild(textNote(builder.toString()));

                        updateSetChoose.appendChild(whenSetOperationEqual);

                        // <when test="update.getUpdateSet('property').operation.name() == 'INC'">
                        //       column = column + 1
                        // </when>
                        final Element whenSetOperationInc = document.createElement("when");
                        whenSetOperationInc.setAttribute("test", "update.getUpdateSet('" + property.getName() + "').operation.name() == 'INC'");
                        final String whenSetOperationIncText = String.format("%s = %s + 1,", property.getColumn(), property.getColumn());
                        whenSetOperationInc.appendChild(textNote(whenSetOperationIncText));
                        updateSetChoose.appendChild(whenSetOperationInc);

                        // <when test="update.getUpdateSet('property').operation.name() == 'INCR'">
                        //       column = column + #{update.getUpdateSet('property').value}
                        // </when>
                        final Element whenSetOperationIncr = document.createElement("when");
                        whenSetOperationIncr.setAttribute("test", "update.getUpdateSet('" + property.getName() + "').operation.name() == 'INCR'");
                        final String whenSetOperationIncrText = String.format("%s = %s + #{update.getUpdateSet('%s').value},", property.getColumn(), property.getColumn(), property.getName());
                        whenSetOperationIncr.appendChild(textNote(whenSetOperationIncrText));
                        updateSetChoose.appendChild(whenSetOperationInc);

                        // <when test="update.getUpdateSet('property').operation.name() == 'DEC'">
                        //       column = column - 1
                        // </when>
                        final Element whenSetOperationDec = document.createElement("when");
                        whenSetOperationDec.setAttribute("test", "update.getUpdateSet('" + property.getName() + "').operation.name() == 'DEC'");
                        final String whenSetOperationDecText = String.format("%s = %s - 1,", property.getColumn(), property.getColumn());
                        whenSetOperationDec.appendChild(textNote(whenSetOperationDecText));
                        updateSetChoose.appendChild(whenSetOperationDec);

                        // <when test="update.getUpdateSet('property').operation.name() == 'DECR'">
                        //       column = column - #{update.getUpdateSet('property').value}
                        // </when>
                        final Element whenSetOperationDecr = document.createElement("when");
                        whenSetOperationDecr.setAttribute("test", "update.getUpdateSet('" + property.getName() + "').operation.name() == 'DECR'");
                        final String whenSetOperationDecrText = String.format("%s = %s - #{update.getUpdateSet('%s').value},", property.getColumn(), property.getColumn(), property.getName());
                        whenSetOperationDecr.appendChild(textNote(whenSetOperationDecrText));
                        updateSetChoose.appendChild(whenSetOperationDecr);
                        ifUpdateContains.appendChild(updateSetChoose);
                        whenUpdateNotNull.appendChild(ifUpdateContains);
                    }
                });
        //        </when>
        choose.appendChild(whenUpdateNotNull);
        //    </choose>
        set.appendChild(choose);
        //</set>
        sql.appendChild(set);
        //</sql>
        context.getNode().appendChild(sql);
    }

    private void appendSqlSelectFragment() {
        /**
         * <sql id="sql-select">
         *     SELECT columns FROM
         * </sql>
         */
        final Element sql = document.createElement("sql");
        sql.setAttribute("id", SQL_SELECT);
        sql.appendChild(textNote("SELECT " + buildSelectColumns() + " FROM"));
        context.getNode().appendChild(sql);
    }

    private Element whenIdNotNull() {
        /*
         * <when test="id != null">
         *     id = #{id}
         * </when>
         */
        final Element whenIdNotNull = document.createElement("when");
        whenIdNotNull.setAttribute("test", "id != null");
        whenIdNotNull.appendChild(textNote(entity.getRequiredIdProperty().getColumn() + " = #{id}"));
        return whenIdNotNull;
    }

    private Element whenIdsNotNull() {
        /*
         * <when test="ids != null">
         *      id IN
         *     <foreach collection="ids" item="id" open="(" separator="," close=")">
         *         #{id}
         *     </foreach>
         * </when>
         */
        final Element whenIdsNotNull = document.createElement("when");
        whenIdsNotNull.setAttribute("test", "ids != null");
        whenIdsNotNull.appendChild(textNote(entity.getRequiredIdProperty().getColumn() + " IN"));
        final Element foreach = document.createElement("foreach");
        foreach.setAttribute("collection", "ids");
        foreach.setAttribute("item", "id");
        foreach.setAttribute("open", "(");
        foreach.setAttribute("separator", ",");
        foreach.setAttribute("close", ")");
        foreach.appendChild(textNote("#{id}"));
        whenIdsNotNull.appendChild(foreach);
        return whenIdsNotNull;
    }
//    ******************************************************************************************************************
//    ******************************************************************************************************************
//    ******************************************************************************************************************

    private Element whenQueryNotNull() {
        /*
         * <when test="query != null">
         *     #{query.sql}
         * </when>
         */
        final Element whenQueryNotNull = document.createElement("when");
        whenQueryNotNull.setAttribute("test", "query != null");
        whenQueryNotNull.appendChild(textNote("#{query.sql}"));
        return whenQueryNotNull;
    }

    /*
     *
     */
    private Element whereElement(Element... when) {
        /**
         *  <where>
         *      <choose>
         *          <when test=""/>
         *          <when test=""/>
         *          <when test=""/>
         *      </choose>
         *  </where>
         */
        final Element where = document.createElement("where");
        final Element choose = document.createElement("choose");
        Arrays.stream(when).forEach(choose::appendChild);
        where.appendChild(choose);
        return where;
    }

    @SuppressWarnings("all")
    private void appendInsertElement() {
        final Element insert = document.createElement("insert");
        insert.setAttribute("id", "insert");
        switch (entity.getPrimaryKeyType()) {
            case AUTO_INC:
                /*
                 * <insert id="insert" useGeneratedKeys="true" keyProperty="id" keyColumn="id">
                 * </insert>
                 */
                insert.setAttribute("useGeneratedKeys", "true");
                insert.setAttribute("keyProperty", entity.getRequiredIdProperty().getName());
                insert.setAttribute("keyColumn", entity.getRequiredIdProperty().getColumn());
                break;
            case UUID:
            case UUID_MD5:
                /*
                 * <selectKey keyProperty="id" resultType="java.lang.String" order="BEFORE">
                 *     SELECT REPLACE(UUID(), '-', '') FROM dual
                 * </selectKey>
                 */
                final Element selectKey = document.createElement("selectKey");
                selectKey.setAttribute("keyProperty", entity.getRequiredIdProperty().getName());
                selectKey.setAttribute("resultType", String.class.getCanonicalName());
                selectKey.setAttribute("order", "BEFORE");
                final Text selectKeyText = document.createTextNode(PrimaryKeyType.UUID == entity.getPrimaryKeyType() ?
                        "SELECT REPLACE(UUID(), '-', '') FROM dual" : "SELECT MD5(REPLACE(UUID(), '-', '')) FROM dual");
                selectKey.appendChild(selectKeyText);
                break;
        }

        /*
         *  INSERT INTO
         *  <include refid="sql-table"/>
         *  (columns)
         *  VALUES
         *  (),()
         *  <include refid="where"/>
         */

        insert.appendChild(textNote("INSERT INTO"));
        insert.appendChild(includeElement(SQL_TABLE));
        insert.appendChild(textNote("("));
        // (column1,column2,multiColumn1,multiColumn2)
        String insertColumns = entity.stream().filter(it -> it.insertable() && !it.hasAnnotation(MultiColumn.class))
                .map(Property::getColumn)
                .collect(Collectors.joining(","));
        String mutilColumns = entity.stream().filter(it -> it.insertable() && it.hasAnnotation(MultiColumn.class))
                .map(it -> {
                    MultiColumn multiColumn = it.findAnnotation(MultiColumn.class);
                    if (multiColumn == null) throw new RuntimeException("");
                    Class multiEntityType = getPropertyJavaType(it);
                    Entity<Property> multiEntity = Entity.from(multiEntityType);
                    return Arrays.stream(multiColumn.properties())
                            .map(multiEntity::getRequiredPersistentProperty)
                            .map(property -> it.getName() + property.getColumn().substring(0, 1).toUpperCase() + property.getColumn().substring(1))
                            .collect(Collectors.joining(","));
                }).collect(Collectors.joining(","));

        insert.appendChild(textNote(mutilColumns != null && mutilColumns.length() > 0 ? insertColumns + "," + mutilColumns : insertColumns));
        insert.appendChild(textNote(") VALUES "));

        /*
         * <foreach collection="list" index="index" item="entity" separator=",">
         *     (#{list[${index}].property,javaType=%s,typeHandler=%s},...
         *     <if test="multi != null">
         *         ,#{list[${index}].multi.property,javaType=%s,typeHandler=%s}
         *     </if>
         *     )
         *     </foreach>
         * </foreach>
         */

        Element foreach = document.createElement("foreach");
        foreach.setAttribute("collection", "list");
        foreach.setAttribute("index", "index");
        foreach.setAttribute("item", "item");
        foreach.setAttribute("separator", ",");
        foreach.appendChild(textNote("("));
        String insertValues = entity.stream().filter(it -> it.insertable() && !it.hasAnnotation(MultiColumn.class))
                //#{list[${index}].property,javaType=%s,typeHandler=%s}
                .map(it -> {
                    final Class javaType = getPropertyJavaType(it);
                    final TypeHandler typeHandler = getPropertyTypeHandler(it);

                    StringBuilder builder = new StringBuilder();
                    builder.append("#{list[${index}].").append(it.getName());
                    if (typeHandler != null) {
                        builder.append(",javaType=").append(javaType.getCanonicalName());
                        builder.append(",typeHandler=").append(typeHandler.getClass().getCanonicalName());
                    }
                    builder.append("}");
                    return builder.toString();

                })
                .collect(Collectors.joining(","));
        foreach.appendChild(textNote(insertValues));

        entity.stream().filter(it -> it.insertable() && it.hasAnnotation(MultiColumn.class))
                .map(it -> {
                    MultiColumn multiColumn = it.findAnnotation(MultiColumn.class);
                    if (multiColumn == null) throw new RuntimeException("");

                    final Element choose = document.createElement("choose");
                    final Element when = document.createElement("when");
                    when.setAttribute("test", "list[index]." + it.getName() + " != null");
                    Class multiEntityType = getPropertyJavaType(it);
                    Entity<Property> multiEntity = Entity.from(multiEntityType);
                    String insertMultiValues = Arrays.stream(multiColumn.properties())
                            .map(multiEntity::getRequiredPersistentProperty)
                            .map(property -> {
                                final Class javaType = getPropertyJavaType(property);
                                final TypeHandler typeHandler = getPropertyTypeHandler(property);
                                //#{list[${index}].multi.property,javaType=%s,typeHandler=%s}
                                StringBuilder builder = new StringBuilder();
                                builder.append("#{list[${index}].").append(it.getName()).append(".").append(property.getName());
                                if (typeHandler != null) {
                                    builder.append(",javaType=").append(javaType.getCanonicalName());
                                    builder.append(",typeHandler=").append(typeHandler.getClass().getCanonicalName());
                                }
                                builder.append("}");
                                return builder.toString();
                            })
                            .collect(Collectors.joining(","));
                    when.appendChild(textNote("," + insertMultiValues));
                    choose.appendChild(when);
                    final Element otherwise = document.createElement("otherwise");
                    final List<String> nullValues = new ArrayList<>();
                    for (int i = 0; i < multiColumn.properties().length; i++) {
                        nullValues.add("null");
                    }
                    otherwise.appendChild(textNote("," + nullValues.stream().collect(Collectors.joining(","))));
                    choose.appendChild(otherwise);
                    return choose;
                }).forEach(foreach::appendChild);
        foreach.appendChild(textNote(")"));
        insert.appendChild(foreach);
        insert.appendChild(whereElement(whenQueryNotNull()));
        context.getNode().appendChild(insert);
    }

    private void appendUpdateElement() {
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
        update.setAttribute("id", "update");
        update.appendChild(textNote("UPDATE"));
        update.appendChild(includeElement(SQL_TABLE));
        update.appendChild(includeElement(SQL_UPDATE));
        update.appendChild(whereElement(whenIdsNotNull(), whenQueryNotNull()));
        context.getNode().appendChild(update);
    }

    private void appendDeleteElement() {
        /**
         *     <delete id="delete">
         *         DELETE FROM
         *         <include refid="sql-table"/>
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
         *     </delete>
         */
        final Element delete = document.createElement("delete");
        delete.setAttribute("id", "delete");
        delete.appendChild(textNote("DELETE FROM"));
        delete.appendChild(includeElement(SQL_TABLE));
        delete.appendChild(whereElement(whenIdsNotNull(), whenQueryNotNull()));
        context.getNode().appendChild(delete);
    }

    private void appendSelectElement() {
        /**
         *
         */
        final Element select = document.createElement("select");
        select.setAttribute("id", "select");
        select.setAttribute("resultMap", type.getSimpleName() + "Map");
        select.appendChild(includeElement(SQL_SELECT));
        select.appendChild(includeElement(SQL_TABLE));
        select.appendChild(whereElement(whenIdsNotNull(), whenQueryNotNull()));
        context.getNode().appendChild(select);
    }

    private void appendSelectOneElement() {
        /**
         *
         */
        final Element select = document.createElement("select");
        select.setAttribute("id", "selectOne");
        select.setAttribute("resultMap", type.getSimpleName() + "Map");
        select.appendChild(includeElement(SQL_SELECT));
        select.appendChild(includeElement(SQL_TABLE));
        select.appendChild(whereElement(whenIdNotNull(), whenQueryNotNull()));
        context.getNode().appendChild(select);
    }

    private String buildSelectColumns() {
        final List<String> columns = new ArrayList<>();
        entity.stream().filter(it -> !it.isTransient())
                .forEach(property -> {
                    if (property.hasAnnotation(MultiColumn.class)) {
                        final Class multiType = getPropertyJavaType(property);
                        final Entity<Property> multiEntity = Entity.from(multiType);
                        Arrays.stream(property.findAnnotation(MultiColumn.class)
                                .properties())
                                .map(multiEntity::getRequiredPersistentProperty)
                                .forEach(multiProperty -> {
                                    final String multiColumn = property.getName() + multiProperty.getColumn().substring(0, 1).toUpperCase() + multiProperty.getColumn().substring(1);
                                    columns.add(multiColumn);
                                });
                    } else {
                        columns.add(property.getColumn());
                    }
                });
        return String.join(",", columns);
    }

    private void appendSelectCountElement() {
        /*
         *     <select id="selectCount" resultType="java.lang.Long">
         *         SELECT COUNT(*) FROM
         *         <include refid="sql-table"/>
         *         <where>
         *
         *     </select>
         */
        final Element selectCount = document.createElement("select");
        selectCount.setAttribute("id", "selectCount");
        selectCount.setAttribute("resultType", Long.class.getCanonicalName());
        Text selectCountText = document.createTextNode("SELECT COUNT(*) FROM");
        selectCount.appendChild(selectCountText);
        // <include refid="sql-table"/>
        selectCount.appendChild(includeElement(SQL_TABLE));
        selectCount.appendChild(whereElement(whenQueryNotNull()));
        context.getNode().appendChild(selectCount);
    }

    private Element includeElement(String refid) {
        final Element includeWhere = document.createElement("include");
        includeWhere.setAttribute("refid", refid);
        return includeWhere;
    }

    private Text textNote(String text) {
        return document.createTextNode(text);
    }


}
