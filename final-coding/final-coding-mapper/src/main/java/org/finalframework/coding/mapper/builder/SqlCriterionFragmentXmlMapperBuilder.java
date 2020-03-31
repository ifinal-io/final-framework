package org.finalframework.coding.mapper.builder;


import org.finalframework.coding.entity.Entity;
import org.finalframework.coding.mapper.TypeHandlers;
import org.finalframework.data.query.operation.CompareOperation;
import org.finalframework.data.query.operation.Operation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Arrays;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-24 21:30:54
 * @since 1.0
 */
public class SqlCriterionFragmentXmlMapperBuilder extends AbsSqlFragmentXmlMapperBuilder {

    public SqlCriterionFragmentXmlMapperBuilder(TypeHandlers typeHandlers) {
        super(typeHandlers);
    }

    @Override
    public String id() {
        return SQL_CRITERION;
    }

    @Override
    protected Element buildSqlFragment(Document document, Entity entity) {
        Element sql = sql(document, id());

        sql.appendChild(choose(document, Arrays.asList(
                singleWhenElement(document, CompareOperation.NULL, "${criterion.criterionTarget} IS NULL"),
                singleWhenElement(document, CompareOperation.NOT_NULL, "${criterion.criterionTarget} IS NOT NULL"),

                singleWhenElement(document, CompareOperation.EQUAL,
                        "${criterion.criterionTarget} = ${criterion.criterionValue}"),
                singleWhenElement(document, CompareOperation.NOT_EQUAL,
                        "${criterion.criterionTarget} != ${criterion.criterionValue}"),
                singleWhenElement(document, CompareOperation.GREAT_THAN,
                        "${criterion.criterionTarget} > ${criterion.criterionValue}"),
                singleWhenElement(document, CompareOperation.GREAT_THAN_EQUAL,
                        "${criterion.criterionTarget} >= ${criterion.criterionValue}"),
                singleWhenElement(document, CompareOperation.LESS_THAN,
                        "${criterion.criterionTarget} < ${criterion.criterionValue}"),
                singleWhenElement(document, CompareOperation.LESS_THAN_EQUAL,
                        "${criterion.criterionTarget} <= ${criterion.criterionValue}"),

                betweenWhenElement(document, CompareOperation.BETWEEN,
                        "${criterion.criterionTarget} BETWEEN ${criterion.criterionMin} AND ${criterion.criterionMax}"),
                betweenWhenElement(document, CompareOperation.NOT_BETWEEN,
                        "${criterion.criterionTarget} NOT BETWEEN ${criterion.criterionMin} AND ${criterion.criterionMax}"),

                singleWhenElement(document, CompareOperation.LIKE,
                        "${criterion.criterionTarget} LIKE ${criterion.criterionValue}"),
                singleWhenElement(document, CompareOperation.NOT_LIKE,
                        "${criterion.criterionTarget} NOT LIKE ${criterion.criterionValue}"),

                collectionWhenElement(document, CompareOperation.IN, "%s IN"),
                collectionWhenElement(document, CompareOperation.NOT_IN, "%s NOT IN")
        )));

        return sql;
    }

    private Element singleWhenElement(Document document, Operation operation, String expression) {
        return whenOrOtherwise(document, test(operation),
                cdata(document, expression)
        );
    }

    private Element betweenWhenElement(Document document, Operation operation, String expression) {
        return whenOrOtherwise(document, test(operation),
                cdata(document, expression)
        );
    }

    private Element collectionWhenElement(Document document, Operation operation, String format) {

        Element when = document.createElement("when");

        when.setAttribute("test", test(operation));

        when.appendChild(cdata(document, String.format(format, "${criterion.criterionTarget}")));

        Element foreach = document.createElement("foreach");
        foreach.setAttribute("collection", "criterion.value");
        foreach.setAttribute("item", "value");
        foreach.setAttribute("open", "(");
        foreach.setAttribute("separator", ",");
        foreach.setAttribute("close", ")");
        foreach.appendChild(textNode(document, "${criterion.criterionValue}"));

        when.appendChild(foreach);

        return when;
    }

    private String test(Operation operation) {
        return String.format("'%s' == criterion.operation.name()", operation.name());
    }

}

