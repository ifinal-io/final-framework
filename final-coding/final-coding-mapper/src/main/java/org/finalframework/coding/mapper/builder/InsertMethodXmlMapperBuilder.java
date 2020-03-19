package org.finalframework.coding.mapper.builder;

import org.finalframework.coding.entity.Entity;
import org.finalframework.coding.mapper.TypeHandlers;
import org.finalframework.data.annotation.Function;
import org.finalframework.data.annotation.LastModified;
import org.finalframework.data.annotation.ReadOnly;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static org.finalframework.data.annotation.enums.PrimaryKeyType.UUID;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-11 15:48:11
 * @since 1.0
 */
public class InsertMethodXmlMapperBuilder extends AbsMethodXmlMapperBuilder {

    private static final String METHOD_INSERT = "insert";
    private static final String METHOD_REPLACE = "replace";
    private static final String METHOD_SAVE = "save";
    private static final Set<String> methods = new HashSet<>(Arrays.asList(METHOD_INSERT, METHOD_REPLACE, METHOD_SAVE));

    public InsertMethodXmlMapperBuilder(TypeHandlers typeHandlers) {
        super(typeHandlers);
    }

    @Override
    public boolean supports(ExecutableElement method) {
        return !method.isDefault() && methods.contains(method.getSimpleName().toString());
    }


    @Override
    public Element buildMethodElement(ExecutableElement method, Document document, Entity entity) {
        final Element insert = document.createElement("insert");
        String methodName = method.getSimpleName().toString();
        insert.setAttribute("id", methodName);

        switch (entity.getPrimaryKeyType()) {
            case AUTO_INC:
                /*
                 * <insert id="insert" useGeneratedKeys="true" keyProperty="id" keyColumn="id">
                 * </insert>
                 */
                insert.setAttribute("useGeneratedKeys", "true");
                /*
                 * mybatis 3.5 + 的版本 keyProperty 需要添加参数名 list.
                 */
                insert.setAttribute("keyProperty", "list." + entity.getRequiredIdProperty().getName());
                insert.setAttribute("keyColumn", entity.getRequiredIdProperty().getColumn());
                break;
            case UUID:
            case UUID_MD5:
                if (METHOD_INSERT.equals(methodName)) {
                    /*
                     * <selectKey keyProperty="id" resultType="java.lang.String" order="BEFORE">
                     *     SELECT REPLACE(UUID(), '-', '') FROM dual
                     * </selectKey>
                     */
                    final Element selectKey = document.createElement("selectKey");
                    selectKey.setAttribute("keyProperty", entity.getRequiredIdProperty().getName());
                    selectKey.setAttribute("resultType", String.class.getCanonicalName());
                    selectKey.setAttribute("order", "BEFORE");

                    Element trim = document.createElement("trim");
                    trim.setAttribute("prefix", "SELECT");
                    trim.setAttribute("suffix", "FROM dual");
                    trim.appendChild(textNode(document, UUID == entity.getPrimaryKeyType() ? "REPLACE(UUID(), '-', '')" : "MD5(REPLACE(UUID(), '-', ''))"));

                    selectKey.appendChild(trim);
//                final Text selectKeyText = document.createTextNode(UUID == entity.getPrimaryKeyType() ?
//                        "SELECT REPLACE(UUID(), '-', '') FROM dual" : "SELECT MD5(REPLACE(UUID(), '-', '')) FROM dual");
//                selectKey.appendChild(selectKeyText);
                    insert.appendChild(selectKey);
                }
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

//        insert.appendChild(textNode(document, "\n\t\tINSERT INTO\n"));
//        insert.appendChild(include(document, SQL_TABLE));

        Element trim = document.createElement("trim");
        if (METHOD_SAVE.equalsIgnoreCase(methodName)) {
            trim.setAttribute("prefix", "INSERT INTO");
        } else if (METHOD_REPLACE.equalsIgnoreCase(methodName)) {
            trim.setAttribute("prefix", "REPLACE INTO");
        } else {
            trim.setAttribute("prefix", "INSERT");
            trim.appendChild(choose(document, Arrays.asList(
                    whenOrOtherwise(document, "ignore == true", textNode(document, " IGNORE INTO")),
                    whenOrOtherwise(document, null, textNode(document, "INTO"))
            )));

        }
        trim.appendChild(include(document, SQL_TABLES));
        insert.appendChild(trim);

        insert.appendChild(insertColumnsElement(document, entity));

        Element values = document.createElement("trim");
        values.setAttribute("prefix", "VALUES");
        values.appendChild(insertValuesElement(document, entity));
        insert.appendChild(values);

        // save

        if (METHOD_SAVE.equals(methodName)) {
            Element onDuplicateKeyUpdate = document.createElement("trim");
            onDuplicateKeyUpdate.setAttribute("prefix", " ON DUPLICATE KEY UPDATE ");

            final List<String> columns = new ArrayList<>();

            entity.stream()
                    .filter(it -> !it.isTransient() && !it.isVirtual() && !it.isSharding()
                            && !(it.hasAnnotation(Function.class) && it.hasAnnotation(ReadOnly.class)))
                    .forEach(property -> {
                        if (property.isReference()) {
                            final Entity multiEntity = property.toEntity();
                            List<String> properties = property.referenceProperties();
                            properties.stream()
                                    .map(multiEntity::getProperty)
                                    .map(multiProperty -> {
//                                    final String table = property.getTable();
//                                    return columnGenerator.generateWriteColumn(table, property, multiProperty);
                                        return TypeHandlers.formatPropertyColumn(property, multiProperty);
                                    })
                                    .map(column -> String.format("%s = values(%s)", column, column))
                                    .forEach(columns::add);
                        } else {
                            String column = TypeHandlers.formatPropertyColumn(null, property);
                            if (property.isVersion()) {
                                columns.add(String.format("%s = values(%s) + 1", column, column));
                            } else if (property.hasAnnotation(LastModified.class)) {
                                columns.add(String.format("%s = NOW()", column));
                            } else {
                                columns.add(String.format("%s = values(%s)", column, column));
                            }
                        }
                    });

            onDuplicateKeyUpdate.appendChild(textNode(document, String.join(",", columns)));


            insert.appendChild(onDuplicateKeyUpdate);

        }


//        insert.appendChild(include(document, SQL_QUERY));

        return insert;
    }


    private Element insertColumnsElement(@NonNull Document document, @NonNull Entity entity) {
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

    private Element buildInsertColumnsElement(@NonNull Document document, @NonNull Entity entity, @Nullable TypeElement view) {

        final String test = view == null ? "view == null" : String.format("view != null and view.getCanonicalName() == '%s'.toString()", view.getQualifiedName().toString());

        final List<String> columns = new ArrayList<>();
        entity.stream()
                .filter(it -> it.isWritable() && !it.isDefault())
                .filter(it -> {
                    if (view == null) {
                        return !it.hasAnnotation(ReadOnly.class);
                    } else {
                        return it.hasView(view);
                    }
                })
                .forEach(property -> {
                    if (property.isReference()) {
                        final Entity multiEntity = property.toEntity();
                        List<String> properties = property.referenceProperties();
                        properties.stream()
                                .map(multiEntity::getProperty)
                                .map(multiProperty -> {
//                                    final String table = property.getTable();
//                                    return columnGenerator.generateWriteColumn(table, property, multiProperty);
                                    return typeHandlers.formatPropertyWriteColumn(property, multiProperty);
                                })
                                .forEach(columns::add);
                    } else {
                        columns.add(typeHandlers.formatPropertyWriteColumn(null, property));
//                        ColumnGenerator columnGenerator = Utils.getPropertyColumnGenerator(property);
//                        columns.add(columnGenerator.generateWriteColumn(property.getTable(), null, property));
                    }
                });
        return whenOrOtherwise(document, test, textNode(document, columns.stream().collect(Collectors.joining(",", "(", ")"))));
    }


    private Element insertValuesElement(@NonNull Document document, @NonNull Entity entity) {
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
        foreach.setAttribute("item", "entity");
        foreach.setAttribute("separator", ",");

        final Element insertValues = document.createElement("choose");
        entity.getViews().stream().map(it -> buildInsertValuesElement(document, entity, it)).forEach(insertValues::appendChild);
        insertValues.appendChild(buildInsertValuesElement(document, entity, null));
        foreach.appendChild(insertValues);
        return foreach;
    }

    private Element buildInsertValuesElement(@NonNull Document document, @NonNull Entity entity, @Nullable TypeElement view) {

        final Element insertValues = document.createElement("when");
        final String test = view == null ? "view == null" : String.format("view != null and view.getCanonicalName() == '%s'.toString()", view.getQualifiedName().toString());
        insertValues.setAttribute("test", test);
        insertValues.appendChild(textNode(document, "("));
        AtomicBoolean first = new AtomicBoolean(true);
        entity.stream()
                .filter(it -> it.isWritable() && !it.isDefault())
                .filter(it -> {
                    if (view == null) {
                        return !it.hasAnnotation(ReadOnly.class);
                    } else {
                        return it.hasView(view);
                    }
                })
                .forEach(property -> {
                    if (property.isReference()) {
                        final Entity multiEntity = property.toEntity();

                        final Element choose = document.createElement("choose");
                        final Element when = document.createElement("when");
//                        final String whenTest = String.format("list[index].%s != null", property.getName());
                        final String whenTest = String.format("entity.%s != null", property.getName());
                        when.setAttribute("test", whenTest);
                        List<String> properties = property.referenceProperties();
                        final String insertMultiValues = properties.stream()
                                .map(multiEntity::getProperty)
                                .map(multiProperty -> {
//                                    final Class javaType = Utils.getPropertyJavaType(multiProperty);
                                    //#{list[${index}].multi.property,javaType=%s,typeHandler=%s}
//                                    final String item = property.placeholder() ? "list[${index}]" : "item";
//                                    final String item = property.placeholder() ? "list[${index}]" : "item";
//                                    ColumnGenerator columnGenerator = Utils.getPropertyColumnGenerator(multiProperty);
//                                    return columnGenerator.generateWriteValue(property, multiProperty, value);

                                    return typeHandlers.formatPropertyValues(property, multiProperty, "entity");
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
//                        //#{list[${index}].property,javaType=%s,typeHandler=%s}
//                        final Class javaType = Utils.getPropertyJavaType(property);
                        StringBuilder builder = new StringBuilder();
//
                        if (!first.get()) {
                            builder.append(",");
                        }
//                        final ColumnGenerator columnGenerator = Utils.getPropertyColumnGenerator(property);
//                        final String item = property.placeholder() ? "list[${index}]" : "item";
//                        final String value = columnGenerator.generateWriteValue(null, property, item);
                        final String value = typeHandlers.formatPropertyValues(null, property, "entity");
                        builder.append(value);
                        first.set(false);
                        insertValues.appendChild(textNode(document, builder.toString()));
                    }
                });
        insertValues.appendChild(textNode(document, ")"));
        return insertValues;
    }
}
