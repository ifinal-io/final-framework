package org.finalframework.coding.mapper.builder;

import org.finalframework.coding.entity.Entity;
import org.finalframework.coding.entity.Property;
import org.finalframework.coding.mapper.TypeHandlers;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-11 15:21:50
 * @since 1.0
 */
public abstract class AbsXmlMapperBuilder {

    protected static final String GENERATED_TAG = "GENERATED-BY-FINAL-FRAMEWORK";

    protected static final String SQL_TABLES = "sql-tables";
    protected static final String SQL_TABLE = "sql-table";

    protected static final String SQL_WHERE_ID = "sql-where-id";
    protected static final String SQL_WHERE_IDS = "sql-where-ids";

    protected static final String SQL_QUERY = "sql-query";
    protected static final String SQL_WHERE_CRITERIA = "sql-where-criteria";
    protected static final String SQL_CRITERIA = "sql-criteria";
    protected static final String SQL_CRITERION = "sql-criterion";
    protected static final String SQL_GROUP = "sql-group";
    protected static final String SQL_ORDER = "sql-order";
    protected static final String SQL_LIMIT = "sql-limit";
    protected static final String SQL_SELECT_COLUMNS = "sql-select-columns";

    protected final TypeHandlers typeHandlers;

    public AbsXmlMapperBuilder(TypeHandlers typeHandlers) {
        this.typeHandlers = typeHandlers;
    }

    /**
     * <pre>
     *     <sql id="id">
     *         <choose>
     *             <when test="tableName != null">
     *                 ${tableName}
     *             </when>
     *             <otherwise>
     *                 tableName
     *             </otherwise>
     *         </choose>
     *     </sql>
     * </pre>
     *
     * @param document
     * @param entity
     * @return
     */
    protected Element table(@NonNull Document document, @NonNull Entity<?> entity) {
        //  <sql id="id">
        final Element sql = document.createElement("sql");
        sql.setAttribute("id", SQL_TABLES);
        //      <choose>
        final Element choose = document.createElement("choose");
        //              <when test="tableName != null">
        final Element whenTableNameNotNull = document.createElement("when");
        whenTableNameNotNull.setAttribute("test", "tableName != null");
        //                  ${tableName}
        whenTableNameNotNull.appendChild(textNode(document, "${tableName}"));
        //              </when>
        choose.appendChild(whenTableNameNotNull);
        //              <otherwise>
        final Element otherwise = document.createElement("otherwise");
        //                  tableName
        otherwise.appendChild(textNode(document, entity.getTable()));
        //              </otherwise>
        choose.appendChild(otherwise);
        //      </choose>
        sql.appendChild(choose);
        return sql;
    }

    protected Element sql(@NonNull Document document, @NonNull String id) {
        Element sql = document.createElement("sql");
        sql.setAttribute("id", id);
        return sql;
    }

    protected Element bind(@NonNull Document document, @NonNull String name, @NonNull String value) {
        Element bind = document.createElement("bind");
        bind.setAttribute("name", name);
        bind.setAttribute("value", value);
        return bind;
    }

    protected Element include(@NonNull Document document, @NonNull String refid) {
        final Element include = document.createElement("include");
        include.setAttribute("refid", refid);
        return include;
    }

    /**
     * <choose>
     * <when test=""/>
     * <when test=""/>
     * <when test=""/>
     * </choose>
     *
     * @param document
     * @param when
     * @return
     */
    protected Element where(@NonNull Document document, @NonNull Element... when) {
        final Element choose = document.createElement("choose");
        Arrays.stream(when).forEach(choose::appendChild);
        return choose;
    }

    protected Node textNode(@NonNull Document document, @NonNull String text) {
        return document.createTextNode(text);
    }

    protected Node cdata(@NonNull Document document, @NonNull String text) {
        return document.createCDATASection(text);
    }

    protected Element ifTest(@NonNull Document document, @NonNull String test) {
        Element element = document.createElement("if");
        element.setAttribute("test", test);
        return element;
    }

    protected Element choose(@NonNull Document document, @NonNull Collection<Element> whenAndOtherWises) {
        final Element choose = document.createElement("choose");
        whenAndOtherWises.forEach(choose::appendChild);
        return choose;
    }

    protected Element whenOrOtherwise(@NonNull Document document, @Nullable String test, @NonNull Node... child) {
        final Element when = document.createElement(test != null ? "when" : "otherwise");
        if (test != null) {
            when.setAttribute("test", test);
        }
        Arrays.stream(child).forEach(when::appendChild);
        return when;
    }


    protected Element whenIdNotNull(@NonNull Document document, @NonNull Entity<?> entity) {
        return whenOrOtherwise(document, "id != null", include(document, SQL_WHERE_ID));
    }

    protected Element whenIdsNotNull(@NonNull Document document, @NonNull Entity<?> entity) {
        return whenOrOtherwise(document, "ids != null", include(document, SQL_WHERE_IDS));
    }

    /**
     * <when test="query != null">
     * #{query.sql}
     * </when>
     */
    protected Element whenQueryNotNull(@NonNull Document document) {
        return whenOrOtherwise(document, "query != null", include(document, SQL_QUERY));
    }

    /**
     * <when test="entity != null">
     * <if test="entity.property != null">
     * AND column = #{entity.property}
     * </if>
     * </when>
     *
     * @param document
     * @param entity
     * @return
     */
    @Deprecated
    protected Element whenEntityNotNull(@NonNull Document document, Entity<Property> entity) {
        final Element whenEntityNotNull = document.createElement("when");
        whenEntityNotNull.setAttribute("test", "entity != null");
        entity.stream()
                .filter(it -> !it.isTransient())
                .forEach(property -> {
                    if (property.isReference()) {

                    } else {
                        Element ifPropertyNotNull = document.createElement("if");
                        ifPropertyNotNull.setAttribute("test", String.format("entity.%s != null", property.getName()));

                        final String column = typeHandlers.formatPropertyColumn(null, property);
                        final String value = typeHandlers.formatPropertyValues(null, property, "entity");

                        ifPropertyNotNull.appendChild(textNode(document, String.format("AND %s = %s", column, value)));
                        whenEntityNotNull.appendChild(ifPropertyNotNull);
                    }
                });
        return whenEntityNotNull;
    }
}
