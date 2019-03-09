package com.ilikly.finalframework.mybatis.builder;


import com.ilikly.finalframework.data.annotation.enums.PrimaryKeyType;
import com.ilikly.finalframework.data.mapping.Entity;
import com.ilikly.finalframework.data.mapping.generator.ColumnGenerator;
import com.ilikly.finalframework.mybatis.Utils;
import org.apache.ibatis.type.TypeHandler;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-09 16:24:28
 * @since 1.0
 */
public class InsertMethodXmlMapperBuilder extends AbsMethodXmlMapperBuilder implements MethodXmlMapperBuilder {

    @Override
    public boolean supports(Method method) {
        return !method.isDefault() && method.getName().equals("insert");
    }

    @Override
    public Element build(Method method, Document document, Entity<?> entity) {
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
         *  <include refid="sql-insert-columns"/>
         *  <include refid="sql-table"/>
         *  <include refid="sql-insert-values"/>
         *  (columns)
         *  VALUES
         *  (),()
         *  <include refid="where"/>
         */

        insert.appendChild(textNode(document, "\n\t\tINSERT INTO\n"));
        insert.appendChild(include(document, SQL_TABLE));
//        insert.appendChild(includeElement(SQL_INSERT_COLUMNS));
        insert.appendChild(insertColumnsElement(document, entity));
        insert.appendChild(textNode(document, "\n\t\tVALUES\n"));
//        insert.appendChild(includeElement(SQL_INSERT_VALUES));
        insert.appendChild(insertValuesElement(document, entity));
        insert.appendChild(where(document, whenQueryNotNull(document)));
        insert.appendChild(include(document, SQL_ORDER));
        insert.appendChild(include(document, SQL_LIMIT));
        return insert;
    }


    private Element insertColumnsElement(@NonNull Document document, @NonNull Entity<?> entity) {
        /**
         * <choose>
         *     <when test="'view' == view">
         *         (column,column)
         *     </when>
         *     <otherwise>
         *         (column,column)
         *     </otherwise>
         * </choose>
         */

        final Element insertColumns = document.createElement("choose");
        entity.getViews().stream().map(view -> buildInsertColumnsElement(document, entity, view)).forEach(insertColumns::appendChild);
        insertColumns.appendChild(buildInsertColumnsElement(document, entity, null));

        return insertColumns;
    }

    private Element buildInsertColumnsElement(@NonNull Document document, @NonNull Entity<?> entity, @Nullable Class<?> view) {

        final String test = view == null ? null : String.format("view != null and view.getCanonicalName() == '%s'.toString()", view.getCanonicalName());

        final List<String> columns = new ArrayList<>();
        entity.stream().filter(it -> !it.isTransient() && it.insertable() && (view == null || it.hasView(view)))
                .forEach(property -> {
                    if (property.isReference()) {
                        final Class multiType = Utils.getPropertyJavaType(property);
                        final Entity<?> multiEntity = Entity.from(multiType);

                        property.referenceProperties().stream()
                                .map(multiEntity::getRequiredPersistentProperty)
                                .map(multiProperty -> {
                                    final String table = property.getTable();
                                    final ColumnGenerator columnGenerator = Utils.getPropertyColumnGenerator(multiProperty);
                                    return columnGenerator.generateWriteColumn(table, property, multiProperty);
                                })
                                .forEach(columns::add);
                    } else {
                        ColumnGenerator columnGenerator = Utils.getPropertyColumnGenerator(property);
                        columns.add(columnGenerator.generateWriteColumn(property.getTable(), null, property));
                    }
                });
        return whenOrOtherwise(document, test, textNode(document, columns.stream().collect(Collectors.joining(",", "(", ")"))));
    }


    private Element insertValuesElement(@NonNull Document document, @NonNull Entity<?> entity) {
        /**
         *      <foreach collection="list" index="index" item="entity" separator=",">
         *          <choose>
         *              <when test="view != null and view == 'view'.toString()">
         *                  (
         *                        #{list[${index}].property,javaType=%s,typeHandler=%s},
         *                        ...
         *                        <choose>
         *                            <when test="list[index].multi != null">
         *                                #{list[${index}].multi.property,javaType=%s,typeHandler=%s},
         *                                #{list[${index}].multi.property,javaType=%s,typeHandler=%s},
         *                            </when>
         *                            <otherwise>
         *                                null,null
         *                            </otherwise>
         *                        </choose>
         *                    )
         *              </when>
         *          </choose>
         *      </foreach>
         */
        Element foreach = document.createElement("foreach");
        foreach.setAttribute("collection", "list");
        foreach.setAttribute("index", "index");
        foreach.setAttribute("item", "item");
        foreach.setAttribute("separator", ",");

        final Element insertValues = document.createElement("choose");
        entity.getViews().stream().map(it -> buildInsertValuesElement(document, entity, it)).forEach(insertValues::appendChild);
        insertValues.appendChild(buildInsertValuesElement(document, entity, null));
        foreach.appendChild(insertValues);
        return foreach;
    }

    private Element buildInsertValuesElement(@NonNull Document document, @NonNull Entity<?> entity, @Nullable Class<?> view) {

        final Element insertValues = document.createElement(view != null ? "when" : "otherwise");

        if (view != null) {
            final String test = String.format("view != null and view.getCanonicalName() == '%s'.toString()", view.getCanonicalName());
            insertValues.setAttribute("test", test);
        }

        insertValues.appendChild(textNode(document, "("));
        AtomicBoolean first = new AtomicBoolean(true);
        entity.stream().filter(it -> !it.isTransient() && it.insertable() && (view == null || it.hasView(view)))
                .forEach(property -> {
                    if (property.isReference()) {

                        final Class multiType = Utils.getPropertyJavaType(property);
                        final Entity<?> multiEntity = Entity.from(multiType);

                        final Element choose = document.createElement("choose");
                        final Element when = document.createElement("when");
                        final String whenTest = String.format("list[index].%s != null", property.getName());
                        when.setAttribute("test", whenTest);
                        final String insertMultiValues = property.referenceProperties().stream()
                                .map(multiEntity::getRequiredPersistentProperty)
                                .map(multiProperty -> {
                                    final Class javaType = Utils.getPropertyJavaType(multiProperty);
                                    final TypeHandler typeHandler = Utils.getPropertyTypeHandler(multiProperty);
                                    //#{list[${index}].multi.property,javaType=%s,typeHandler=%s}
                                    final String value = property.placeholder() ? "list[${index}]" : "item";
                                    ColumnGenerator columnGenerator = Utils.getPropertyColumnGenerator(multiProperty);
                                    return columnGenerator.generateWriteValue(property, multiProperty, value);
                                })
                                .collect(Collectors.joining(",\n"));
                        when.appendChild(textNode(document, first.get() ? insertMultiValues : "," + insertMultiValues));
                        choose.appendChild(when);
                        final Element otherwise = document.createElement("otherwise");
                        final List<String> nullValues = new ArrayList<>();
                        for (int i = 0; i < property.referenceProperties().size(); i++) {
                            nullValues.add("null");
                        }
                        final String otherWiseText = first.get() ? String.join(",", nullValues) : "," + String.join(",", nullValues);
                        otherwise.appendChild(textNode(document, otherWiseText));
                        choose.appendChild(otherwise);
                        first.set(false);
                        insertValues.appendChild(choose);
                    } else {
                        //#{list[${index}].property,javaType=%s,typeHandler=%s}
                        final Class javaType = Utils.getPropertyJavaType(property);
                        final TypeHandler typeHandler = Utils.getPropertyTypeHandler(property);
                        StringBuilder builder = new StringBuilder();

                        if (!first.get()) {
                            builder.append(",");
                        }
                        final ColumnGenerator columnGenerator = Utils.getPropertyColumnGenerator(property);
                        final String item = property.placeholder() ? "list[${index}]" : "item";
                        final String value = columnGenerator.generateWriteValue(null, property, item);
                        builder.append(value);
                        first.set(false);
                        insertValues.appendChild(textNode(document, builder.toString()));
                    }
                });
        insertValues.appendChild(textNode(document, ")"));


        return insertValues;
    }

}
