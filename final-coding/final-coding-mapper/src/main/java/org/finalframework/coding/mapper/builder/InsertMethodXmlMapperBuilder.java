package org.finalframework.coding.mapper.builder;

import org.finalframework.coding.entity.Entity;
import org.finalframework.coding.entity.Property;
import org.finalframework.coding.mapper.TypeHandlers;
import org.finalframework.data.annotation.enums.ReferenceMode;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.util.*;
import java.util.stream.Collectors;

import static org.finalframework.data.annotation.enums.PrimaryKeyType.UUID;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-11 15:48:11
 * @see SqlOnDuplicateKeyFragmentXmlMapperBuilder
 * @see org.finalframework.data.repository.Repository#insert(String, Class, boolean, Collection)
 * @see org.finalframework.data.repository.Repository#replace(String, Class, Collection)
 * @see org.finalframework.data.repository.Repository#save(String, Class, Collection)
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
                    trim.appendChild(textNode(document, UUID == entity.getPrimaryKeyType() ? "REPLACE(UUID(), '-', '')"
                            : "MD5(REPLACE(UUID(), '-', ''))"));

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
            insert.appendChild(include(document, SQL_ON_DUPLICATE_KEY));
        }

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
        entity.getViews().stream().map(view -> buildInsertColumnsElement(document, entity, view))
                .forEach(insertColumns::appendChild);
        insertColumns.appendChild(buildInsertColumnsElement(document, entity, null));

        return insertColumns;
    }

    private Element buildInsertColumnsElement(@NonNull Document document, @NonNull Entity entity,
                                              @Nullable TypeElement view) {

        final String test = view == null ? "view == null" : String
                .format("view != null and view.getCanonicalName() == '%s'.toString()", view.getQualifiedName().toString());

        final List<String> columns = new ArrayList<>();
        entity.stream()
                .filter(Property::isWriteable)
                .filter(it -> view == null || it.hasView(view))
                .forEach(property -> {
                    if (property.isReference()) {
                        final Entity multiEntity = property.toEntity();
                        List<String> properties = property.referenceProperties();
                        properties.stream()
                                .map(multiEntity::getProperty)
                                .map(multiProperty -> {
//                                    final String table = property.getTable();
//                                    return columnGenerator.generateWriteColumn(table, property, multiProperty);
                                    return typeHandlers.formatPropertyWriteColumn(entity, property, multiProperty);
                                })
                                .forEach(columns::add);
                    } else {
                        columns.add(typeHandlers.formatPropertyWriteColumn(entity, null, property));
//                        ColumnGenerator columnGenerator = Utils.getPropertyColumnGenerator(property);
//                        columns.add(columnGenerator.generateWriteColumn(property.getTable(), null, property));
                    }
                });
        return whenOrOtherwise(document, test,
                textNode(document, columns.stream().collect(Collectors.joining(",", "(", ")"))));
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

        entity.stream()
                .filter(Property::isWriteable)
                .forEach(property -> {
                    if (property.isReference()) {
                        final Entity multiEntity = property.toEntity();
                        property.referenceProperties()
                                .stream()
                                .map(multiEntity::getRequiredProperty)
                                .forEach(referenceProperty -> {
                                    String name = referenceProperty.isIdProperty() && ReferenceMode.SIMPLE == property.referenceMode()
                                            ? property.getName()
                                            : property.getName() + referenceProperty.getColumn().substring(0, 1).toUpperCase() + referenceProperty.getColumn().substring(1);
                                    String value = String.format("entity.%s != null ? entity.%s.%s : null", property.getName(), property.getName(), referenceProperty.getName());
                                    final Element bind = bind(document, name, value);

                                    foreach.appendChild(bind);

                                });
                    } else {
                        foreach.appendChild(bind(document, property.getName(), "entity." + property.getName()));
                    }
                });


        Element trim = document.createElement("trim");
        trim.setAttribute("prefix", "(");
        trim.setAttribute("suffix", ")");
        trim.setAttribute("suffixOverrides", ",");
        final Element insertValues = document.createElement("choose");


        entity.getViews().stream().map(it -> buildInsertValuesElement(document, entity, it))
                .forEach(insertValues::appendChild);
        insertValues.appendChild(buildInsertValuesElement(document, entity, null));
        trim.appendChild(insertValues);
        foreach.appendChild(trim);
        return foreach;
    }

    private Element buildInsertValuesElement(@NonNull Document document, @NonNull Entity entity,
                                             @Nullable TypeElement view) {


        final Element insertValues = document.createElement("when");
        final String test = view == null ? "view == null" : String
                .format("view != null and view.getCanonicalName() == '%s'.toString()", view.getQualifiedName().toString());
        insertValues.setAttribute("test", test);
//        insertValues.appendChild(textNode(document, "("));
        entity.stream()
                .filter(Property::isWriteable)
                .filter(it -> view == null || it.hasView(view))
                .forEach(property -> {
                    if (property.isReference()) {

                        final Entity multiEntity = property.toEntity();
                        property.referenceProperties()
                                .stream()
                                .map(multiEntity::getRequiredProperty)
                                .forEach(referenceProperty -> {
                                    String name = referenceProperty.isIdProperty() && ReferenceMode.SIMPLE == property.referenceMode()
                                            ? property.getName()
                                            : property.getName() + referenceProperty.getColumn().substring(0, 1).toUpperCase() + referenceProperty.getColumn().substring(1);
//                                    String value = String.format("entity.%s != null ? entity.%s.%s : null", property.getName(), property.getName(), referenceProperty.getName());
//                                    final Element bind = bind(document, name, value);
//                                    bind.appendChild(textNode(document, String.format("#{%s},", name)));
//                                    insertValues.appendChild(bind);
//                                    insertValues.appendChild(textNode(document, ","));

                                    final StringBuilder builder = new StringBuilder();
                                    builder.append("#{");

                                    builder.append(name);

                                    final TypeElement javaType = referenceProperty.getJavaTypeElement();
                                    builder.append(",javaType=").append(javaType.getQualifiedName().toString());

                                    final TypeElement typeHandler = typeHandlers.getTypeHandler(referenceProperty);
                                    if (typeHandler != null) {
                                        builder.append(",typeHandler=").append(typeHandler.getQualifiedName().toString());
                                    }

                                    builder.append("},");

                                    insertValues.appendChild(textNode(document, builder.toString()));

                                });

                    } else {
//                        //#{list[${index}].property,javaType=%s,typeHandler=%s}
                        final StringBuilder builder = new StringBuilder();
                        builder.append("#{");

                        builder.append(property.getName());

                        final TypeElement javaType = property.getJavaTypeElement();
                        builder.append(",javaType=").append(javaType.getQualifiedName().toString());

                        final TypeElement typeHandler = typeHandlers.getTypeHandler(property);
                        if (typeHandler != null) {
                            builder.append(",typeHandler=").append(typeHandler.getQualifiedName().toString());
                        }

                        builder.append("},");

                        insertValues.appendChild(textNode(document, builder.toString()));
                    }
                });
//        insertValues.appendChild(textNode(document, ")"));
        return insertValues;
    }
}
