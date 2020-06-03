package org.finalframework.coding.mapper.builder;


import java.util.Arrays;
import org.finalframework.coding.entity.Entity;
import org.finalframework.coding.mapper.SQLConstants;
import org.finalframework.coding.mapper.TypeHandlers;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author likly
 * @version 1.0
 * @date 2020-06-01 14:31:37
 * @since 1.0
 */
public class SqlCriterionValueFragmentXmlMapperBuilder extends AbsSqlFragmentXmlMapperBuilder {

    private static final Integer MAX_LOOP = 3;
    private final Integer loop;

    public SqlCriterionValueFragmentXmlMapperBuilder(TypeHandlers typeHandlers, Integer loop) {
        super(typeHandlers);
        this.loop = loop;
    }

    @Override
    protected Element buildSqlFragment(Document document, Entity entity) {
        final Element sql = sql(document, id());
        final Element whenProperty = document.createElement("when");
        whenProperty
            .setAttribute("test", "@org.finalframework.data.query.criterion.CriterionTypes@isProperty(${value})");
        whenProperty.appendChild(cdata(document, "\\${${value}.column}"));

        final Element whenCollection = document.createElement("when");
        whenCollection
            .setAttribute("test", "@org.finalframework.data.query.criterion.CriterionTypes@isCollection(${value})");

        Element foreach = document.createElement("foreach");
        foreach.setAttribute("collection", "${value}");
        foreach.setAttribute("item", "item");
        foreach.setAttribute("separator", ",");
        foreach.appendChild(textNode(document, "#{item}"));
        whenCollection.appendChild(foreach);

        final Element whenValue = document.createElement("when");
        whenValue.setAttribute("test", "@org.finalframework.data.query.criterion.CriterionTypes@isValue(${value})");
        if (loop < MAX_LOOP) {
            whenValue.appendChild(cdata(document, "${value}.value"));
//            whenValue.appendChild(include(document, SQLConstants.SQL_CRITERION_VALUE +  "-" + (loop + 1),
//                property(document, "value", "${value}.value")));

        }
        final Element whenFunction = document.createElement("when");
        whenFunction
            .setAttribute("test", "@org.finalframework.data.query.criterion.CriterionTypes@isFunction(${value})");
        if (loop < MAX_LOOP) {
            whenFunction.appendChild(
                include(document, SQLConstants.SQL_CRITERION_FUNCTION + (loop == 0 ? "" : "-" + loop),
                    property(document, "function", "${value}")));
        }
        sql.appendChild(choose(document, Arrays.asList(
            whenProperty, whenFunction, whenCollection, whenValue,
            whenOrOtherwise(document, null, cdata(document, "#{${value}}"))
        )));

        return sql;
    }


    @Override
    public String id() {
        return SQLConstants.SQL_CRITERION_VALUE + (loop == 0 ? "" : "-" + loop);
    }
}

