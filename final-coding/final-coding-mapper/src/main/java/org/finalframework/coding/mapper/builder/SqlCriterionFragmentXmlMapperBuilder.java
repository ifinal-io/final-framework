package org.finalframework.coding.mapper.builder;


import org.finalframework.coding.entity.Entity;
import org.finalframework.coding.entity.Property;
import org.finalframework.data.query.criterion.operator.CriterionOperator;
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

    @Override
    public String id() {
        return SQL_CRITERION;
    }

    @Override
    protected Element buildSqlFragment(Document document, Entity<Property> entity) {
        Element sql = sql(document, id());

        sql.appendChild(choose(document, Arrays.asList(
                singleWhenElement(document, CriterionOperator.NULL, "${criterion.criterionTarget} IS NULL"),
                singleWhenElement(document, CriterionOperator.NOT_NULL, "${criterion.criterionTarget} IS NOT NULL"),

                singleWhenElement(document, CriterionOperator.EQUAL, "${criterion.criterionTarget} = ${criterion.criterionValue}"),
                singleWhenElement(document, CriterionOperator.NOT_EQUAL, "${criterion.criterionTarget} != ${criterion.criterionValue}"),
                singleWhenElement(document, CriterionOperator.GREATER_THAN, "${criterion.criterionTarget} > ${criterion.criterionValue}"),
                singleWhenElement(document, CriterionOperator.GREATER_THAN_EQUAL, "${criterion.criterionTarget} >= ${criterion.criterionValue}"),
                singleWhenElement(document, CriterionOperator.LESS_THAN, "${criterion.criterionTarget} < ${criterion.criterionValue}"),
                singleWhenElement(document, CriterionOperator.LESS_THAN_EQUAL, "${criterion.criterionTarget} <= ${criterion.criterionValue}"),

                betweenWhenElement(document, CriterionOperator.BETWEEN, "${criterion.criterionTarget} BETWEEN ${criterion.functionMin} AND ${criterion.functionMax}"),
                betweenWhenElement(document, CriterionOperator.NOT_BETWEEN, "${criterion.criterionTarget} NOT BETWEEN ${criterion.functionMin} AND ${criterion.functionMax}"),

                singleWhenElement(document, CriterionOperator.LIKE, "${criterion.criterionTarget} LIKE ${criterion.criterionValue}"),
                singleWhenElement(document, CriterionOperator.NOT_LIKE, "${criterion.criterionTarget} NOT LIKE ${criterion.criterionValue}"),

                collectionWhenElement(document, CriterionOperator.IN, "%s IN"),
                collectionWhenElement(document, CriterionOperator.NOT_IN, "%s NOT IN")
        )));

        return sql;
    }

    private Element singleWhenElement(Document document, CriterionOperator operator, String expression) {
        return whenOrOtherwise(document, test(operator),
                cdata(document, expression)
        );
    }

    private Element betweenWhenElement(Document document, CriterionOperator operator, String expression) {
        return whenOrOtherwise(document, test(operator),
                cdata(document, expression)
        );
    }

    private Element collectionWhenElement(Document document, CriterionOperator operator, String format) {

        Element when = document.createElement("when");

        when.setAttribute("test", test(operator));

        when.appendChild(cdata(document, String.format(format, "${criterion.column}")));

        Element foreach = document.createElement("foreach");
        foreach.setAttribute("collection", "criterion.value");
        foreach.setAttribute("item", "value");
        foreach.setAttribute("open", "(");
        foreach.setAttribute("separator", ",");
        foreach.setAttribute("close", ")");
        foreach.appendChild(textNode(document, "${criterion.functionValue}"));

        when.appendChild(foreach);

        return when;
    }

    private String test(CriterionOperator operator) {
        return String.format("'%s' == criterion.operator.name", operator.getName());
    }

}

