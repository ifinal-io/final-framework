package org.finalframework.coding.mapper.builder;

import java.util.ArrayList;
import java.util.List;
import org.finalframework.coding.entity.Entity;
import org.finalframework.coding.mapper.TypeHandlers;
import org.finalframework.data.annotation.Function;
import org.finalframework.data.annotation.LastModified;
import org.finalframework.data.annotation.ReadOnly;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author sli
 * @version 1.0
 * @date 2020-03-19 20:10:11
 * @since 1.0
 */
public class SqlOnDuplicateKeyFragmentXmlMapperBuilder extends AbsSqlFragmentXmlMapperBuilder {

    public SqlOnDuplicateKeyFragmentXmlMapperBuilder(TypeHandlers typeHandlers) {
        super(typeHandlers);
    }

    @Override
    public String id() {
        return SQL_ON_DUPLICATE_KEY;
    }


    @Override
    protected Element buildSqlFragment(Document document, Entity entity) {
        Element sql = sql(document, id());
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
                            .filter(it -> !it.isSharding())
                        .forEach(multiProperty -> {
                            String column = TypeHandlers.formatPropertyColumn(property, multiProperty);
                            if (property.isVersion()) {
                                columns.add(String.format("%s = values(%s) + 1", column, column));
                            } else if (property.hasAnnotation(LastModified.class)) {
                                columns.add(String.format("%s = NOW()", column));
                            } else {
                                columns.add(String.format("%s = values(%s)", column, column));
                            }
                        });
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

        sql.appendChild(onDuplicateKeyUpdate);
        return sql;
    }
}
