package org.finalframework.coding.mapper.builder;


import org.finalframework.coding.entity.Entity;
import org.finalframework.coding.entity.Property;
import org.finalframework.data.query.criterion.operator.CriterionOperator;
import org.finalframework.data.query.criterion.operator.DefaultCriterionOperator;
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

//                whenOrOtherwise(document, test(DefaultCriterionOperator.EQUAL), cdata(document, "${criterion.column} = #{criterion.value}")),
//                whenOrOtherwise(document, test(DefaultCriterionOperator.NOT_EQUAL), cdata(document, "${criterion.column} != #{criterion.value}")),
//                whenOrOtherwise(document, test(DefaultCriterionOperator.GREATER_THAN), cdata(document, "${criterion.column} > #{criterion.value}")),
//                whenOrOtherwise(document, test(DefaultCriterionOperator.GREATER_THAN_EQUAL), cdata(document, "${criterion.column} >= #{criterion.value}")),
//                whenOrOtherwise(document, test(DefaultCriterionOperator.LESS_THAN), cdata(document, "${criterion.column} < #{criterion.value}")),
//                whenOrOtherwise(document, test(DefaultCriterionOperator.LESS_THAN_EQUAL), cdata(document, "${criterion.column} <= #{criterion.value}")),

//                whenOrOtherwise(document, test(DefaultCriterionOperator.BETWEEN), cdata(document, "${criterion.column} BETWEEN #{criterion.min} AND #{criterion.max}")),
//                whenOrOtherwise(document, test(DefaultCriterionOperator.NOT_BETWEEN), cdata(document, "${criterion.column} NOT BETWEEN #{criterion.min} AND #{criterion.max}")),

//                whenOrOtherwise(document, test(DefaultCriterionOperator.LIKE), cdata(document, "${criterion.column} LIKE CONCAT('${criterion.prefix}',#{criterion.value},'${criterion.suffix}')")),
//                whenOrOtherwise(document, test(DefaultCriterionOperator.NOT_LIKE), cdata(document, "${criterion.column} NOT LIKE CONCAT('${criterion.prefix}',#{criterion.value},'${criterion.suffix}')")),


                singleWhenElement(document, DefaultCriterionOperator.NULL, "${criterion.column} IS NULL"),
                singleWhenElement(document, DefaultCriterionOperator.NOT_NULL, "${criterion.column} IS NOT NULL"),

                singleWhenElement(document, DefaultCriterionOperator.EQUAL, "${criterion.column} = ${criterion.functionValue}"),
                singleWhenElement(document, DefaultCriterionOperator.NOT_EQUAL, "${criterion.column} != ${criterion.functionValue}"),
                singleWhenElement(document, DefaultCriterionOperator.GREATER_THAN, "${criterion.column} > ${criterion.functionValue}"),
                singleWhenElement(document, DefaultCriterionOperator.GREATER_THAN_EQUAL, "${criterion.column} >= ${criterion.functionValue}"),
                singleWhenElement(document, DefaultCriterionOperator.LESS_THAN, "${criterion.column} < ${criterion.functionValue}"),
                singleWhenElement(document, DefaultCriterionOperator.LESS_THAN_EQUAL, "${criterion.column} <= ${criterion.functionValue}"),

                betweenWhenElement(document, DefaultCriterionOperator.BETWEEN, "${criterion.column} BETWEEN ${criterion.functionMin} AND ${criterion.functionMax}"),
                betweenWhenElement(document, DefaultCriterionOperator.NOT_BETWEEN, "${criterion.column} NOT BETWEEN ${criterion.functionMin} AND ${criterion.functionMax}"),

                singleWhenElement(document, DefaultCriterionOperator.LIKE, "${criterion.column} LIKE ${criterion.functionValue}"),
                singleWhenElement(document, DefaultCriterionOperator.NOT_LIKE, "${criterion.column} NOT LIKE ${criterion.functionValue}"),

                collectionWhenElement(document, DefaultCriterionOperator.IN, "%s IN"),
                collectionWhenElement(document, DefaultCriterionOperator.NOT_IN, "%s NOT IN")
        )));

        return sql;
    }

    private Element singleWhenElement(Document document, CriterionOperator operator, String expression) {
        return whenOrOtherwise(document, test(operator),
                bind(document, "value", "criterion.value"),
                cdata(document, expression)
        );
    }

    private Element betweenWhenElement(Document document, CriterionOperator operator, String expression) {
        return whenOrOtherwise(document, test(operator),
                bind(document, "min", "criterion.min"),
                bind(document, "max", "criterion.max"),
                cdata(document, expression)
        );
    }

    private Element collectionWhenElement(Document document, CriterionOperator operator, String format) {

        Element when = document.createElement("when");

        when.setAttribute("test", test(operator));

        Element values = bind(document, "values", "criterion.value");

        when.appendChild(values);

        when.appendChild(cdata(document, String.format(format, "${criterion.column}")));

        Element foreach = document.createElement("foreach");
        foreach.setAttribute("collection", "values");
        foreach.setAttribute("item", "value");
        foreach.setAttribute("open", "(");
        foreach.setAttribute("separator", ",");
        foreach.setAttribute("close", ")");
//        foreach.appendChild(bind(document, "value", "cri"));
        foreach.appendChild(textNode(document, "${criterion.functionValue}"));

        when.appendChild(foreach);

        return when;
    }

    private String test(CriterionOperator operator) {
        return String.format("'%s' == criterion.operator.name", operator.name());
    }

}

