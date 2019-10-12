package org.finalframework.mybatis.coding.mapper.builder;

import org.finalframework.data.coding.entity.Entity;
import org.finalframework.data.coding.entity.Property;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.lang.model.element.ExecutableElement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-12 11:01:11
 * @since 1.0
 */
public class SelectMethodXmlMapperBuilder extends AbsXmlMapperBuilder implements MethodXmlMapperBuilder {

    private static final String METHOD_SELECT = "select";
    private static final String METHOD_SELECT_ONE = "selectOne";
    private static final Set<String> methods = new HashSet<>(Arrays.asList(METHOD_SELECT, METHOD_SELECT_ONE));

    @Override
    public boolean supports(ExecutableElement method) {
        return !method.isDefault() && methods.contains(method.getSimpleName().toString());
    }

    @Override
    public Element build(ExecutableElement method, Document document, Entity<Property> entity) {
        final Element select = document.createElement("select");
        select.setAttribute("id", method.getSimpleName().toString());
        String methodName = entity.getSimpleName();
        select.setAttribute("resultMap", methodName + "Map");
        select.appendChild(textNode(document, "SELECT"));
        select.appendChild(include(document, SQL_SELECT_COLUMNS));
        select.appendChild(textNode(document, "FROM"));
        select.appendChild(include(document, SQL_TABLE));
        select.appendChild(where(document,
                METHOD_SELECT_ONE.equals(methodName) ? whenIdNotNull(document, entity) : whenIdsNotNull(document, entity),
                whenQueryNotNull(document)
        ));
        select.appendChild(include(document, SQL_ORDER));
        if (METHOD_SELECT_ONE.equals(methodName)) {
            // selectOne 默认添加 LIMIT 1
            select.appendChild(textNode(document, " LIMIT 1"));
        } else {
            select.appendChild(include(document, SQL_LIMIT));
        }
        return select;
    }

}
