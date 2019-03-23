package org.finalframework.mybatis.builder;


import org.finalframework.data.mapping.Entity;
import org.finalframework.data.mapping.Property;
import org.finalframework.data.mapping.generator.ColumnGenerator;
import org.finalframework.mybatis.Utils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <pre>
 *     <select id="select" result="ResultMap">
 *          SELECT
 *          <choose>
 *              <when test="view != null && view == 'view'.toString()">
 *                  columns
 *              </when>
 *          </choose>
 *          FROM
 *          <include id="sql-table"/>
 *          <where>
 *             <choose>
 *                 <when test="ids != null">id IN<foreach close=")" collection="ids" item="id" open="(" separator=",">#{id}</foreach>
 *                 </when>
 *                 <otherwise test="query != null and query.criteria != null">${query.criteria.sql}</otherwise>
 *             </choose>
 *          </where>
 *          <include id="sql-order"/>
 *          <include id="sql-limit"/>
 *     </select>
 * </pre>
 *
 * @author likly
 * @version 1.0
 * @date 2019-03-09 17:50:55
 * @since 1.0
 */
public class SelectMethodXmlMapperBuilder extends AbsMethodXmlMapperBuilder implements MethodXmlMapperBuilder {

    private static final String SQL_SELECT_COLUMNS = "sql-select-columns";
    private static final String METHOD_SELECT = "select";
    private static final String METHOD_SELECT_ONE = "selectOne";
    private static final Set<String> methods = new HashSet<>(Arrays.asList(METHOD_SELECT, METHOD_SELECT_ONE));

    @Override
    public boolean supports(Method method) {
        return !method.isDefault() && methods.contains(method.getName());
    }

    @Override
    public Element build(Method method, Document document, Entity<?> entity) {
        final Element select = document.createElement("select");
        select.setAttribute("id", method.getName());
        select.setAttribute("resultMap", entity.getType().getSimpleName() + "Map");
        select.appendChild(textNode(document, "SELECT"));
        select.appendChild(include(document, SQL_SELECT_COLUMNS));
        select.appendChild(textNode(document, "FROM"));
        select.appendChild(include(document, SQL_TABLE));
        select.appendChild(where(document, METHOD_SELECT_ONE.equals(method.getName()) ? whenIdNotNull(document, entity) : whenIdsNotNull(document, entity), whenQueryNotNull(document)));
        select.appendChild(include(document, SQL_ORDER));
        if (METHOD_SELECT_ONE.equals(method.getName())) {
            // selectOne 默认添加 LIMIT 1
            select.appendChild(textNode(document, " LIMIT 1"));
        } else {
            select.appendChild(include(document, SQL_LIMIT));
        }
        return select;
    }

    @Override
    public Collection<Element> sqlFragments(Method method, Document document, Entity<?> entity) {
        return Arrays.asList(selectColumnsSqlFragment(document, entity));
    }

    private Element selectColumnsSqlFragment(@NonNull Document document, @NonNull Entity<?> entity) {
        final Element sql = document.createElement("sql");
        sql.setAttribute("id", SQL_SELECT_COLUMNS);

        final List<Element> elements = entity.getViews().stream().map(view -> whenViewSelectColumns(document, entity, view)).collect(Collectors.toList());
        elements.add(whenViewSelectColumns(document, entity, null));

        sql.appendChild(choose(document, elements));

        return sql;
    }

    private Element whenViewSelectColumns(@NonNull Document document, @NonNull Entity<?> entity, @Nullable Class<?> view) {
        final List<String> columns = new ArrayList<>();
        entity.stream().filter(it -> !it.isTransient() && it.selectable() && (view == null || it.hasView(view)))
                .forEach(property -> {
                    if (property.isReference()) {
                        final Class multiType = Utils.getPropertyJavaType(property);
                        final Entity<?> multiEntity = Entity.from(multiType);
                        property.referenceProperties().stream()
                                .map(multiEntity::getRequiredPersistentProperty)
                                .filter(Property::selectable)
                                .forEach(multiProperty -> {
                                    ColumnGenerator columnGenerator = Utils.getPropertyColumnGenerator(property);
                                    columns.add(columnGenerator.generateReadColumn(property.getTable(), property, multiProperty));
                                });
                    } else {

                        ColumnGenerator columnGenerator = Utils.getPropertyColumnGenerator(property);
                        columns.add(columnGenerator.generateReadColumn(property.getTable(), null, property));
                    }
                });

        final String test = view == null ? null : String.format("view != null and view.getCanonicalName() == '%s'.toString()", view.getCanonicalName());

        return whenOrOtherwise(document, test, textNode(document, String.join(",", columns)));
    }
}
