package org.finalframework.coding.mapper.builder;

import org.finalframework.coding.entity.Entity;
import org.finalframework.coding.entity.Property;
import org.finalframework.coding.mapper.TypeHandlers;
import org.finalframework.data.annotation.ReadOnly;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.lang.model.element.TypeElement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-12 11:23:34
 * @since 1.0
 */
public class SqlSelectColumnsFragmentXmlMapperBuilder extends AbsSqlFragmentXmlMapperBuilder {

    public SqlSelectColumnsFragmentXmlMapperBuilder(TypeHandlers typeHandlers) {
        super(typeHandlers);
    }

    @Override
    public String id() {
        return SQL_SELECT_COLUMNS;
    }

    @Override
    protected Element buildSqlFragment(Document document, Entity entity) {
        final Element sql = document.createElement("sql");
        sql.setAttribute("id", id());

        final List<Element> elements = entity.getViews().stream().map(view -> whenViewSelectColumns(document, entity, view)).collect(Collectors.toList());
        elements.add(whenViewSelectColumns(document, entity, null));

        sql.appendChild(choose(document, elements));

        return sql;
    }

    private Element whenViewSelectColumns(@NonNull Document document, @NonNull Entity entity, @Nullable TypeElement view) {
        final List<String> columns = new ArrayList<>();
        entity.stream().filter(it -> !it.isTransient() && it.isWriteOnly() && !it.isVirtual())
                .filter(it -> {
                    if (view == null) {
                        if (it.hasAnnotation(ReadOnly.class)) {
                            return false;
                        }
                        return true;
                    } else {
                        return it.hasView(view);
                    }
                })
                .forEach(property -> {
                    if (property.isReference()) {
                        final Entity multiEntity = property.toEntity();
                        List<String> referenceProperties = property.referenceProperties();
                        referenceProperties.stream()
                                .map(multiEntity::getProperty)
                                .filter(Property::isWriteOnly)
                                .forEach(multiProperty -> {
                                    columns.add(typeHandlers.formatPropertyReadColumn(property, multiProperty));
                                });
                    } else {
                        columns.add(typeHandlers.formatPropertyReadColumn(null, property));
                    }
                });

        final String test = view == null ? null : String.format("view != null and view.getCanonicalName() == '%s'.toString()", view.getQualifiedName().toString());

        return whenOrOtherwise(document, test, textNode(document, String.join(",", columns)));
    }
}
