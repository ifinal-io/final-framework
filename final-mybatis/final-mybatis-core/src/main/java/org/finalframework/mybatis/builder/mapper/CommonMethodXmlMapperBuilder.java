package org.finalframework.mybatis.builder.mapper;


import org.finalframework.data.mapping.Entity;
import org.finalframework.data.mapping.Property;
import org.finalframework.data.mapping.generator.ColumnGenerator;
import org.finalframework.mybatis.Utils;
import org.springframework.lang.NonNull;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-09 16:25:39
 * @since 1.0
 */
public class CommonMethodXmlMapperBuilder extends AbsMethodXmlMapperBuilder {

    @Deprecated
    private static final String SQL_SELECT = "sql-select";

    @Override
    public boolean supports(Method method) {
        return true;
    }

    @Override
    public Element build(Method method, Document document, Entity<?> entity) {
        return null;
    }

    @Override
    public Collection<Element> sqlFragments(Method method, Document document, Entity<?> entity) {
        return Arrays.asList(table(document, entity), sqlSelectFragment(document, entity), order(document), limit(document));
    }


    @Deprecated
    private Element sqlSelectFragment(@NonNull Document document, @NonNull Entity<?> entity) {
        /**
         * <sql id="sql-select">
         *     SELECT columns FROM
         * </sql>
         */
        final Element sql = document.createElement("sql");
        sql.setAttribute("id", SQL_SELECT);
        sql.appendChild(textNode(document, "SELECT " + buildSelectColumns(entity) + " FROM"));
        return sql;
    }


    @Deprecated
    @SuppressWarnings("all")
    private String buildSelectColumns(@NonNull Entity<?> entity) {
        final List<String> columns = new ArrayList<>();
        entity.stream().filter(it -> !it.isTransient() && it.selectable())
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
        return String.join(",", columns);
    }

}
