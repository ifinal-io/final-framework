package org.finalframework.coding.mapper.builder;


import org.finalframework.coding.entity.Entity;
import org.finalframework.coding.mapper.SQLConstants;
import org.finalframework.coding.mapper.TypeHandlers;
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

                isNull(document, Operation.CompareOperation.NULL),
                isNotNull(document, Operation.CompareOperation.NOT_NULL),

                compare(document, Operation.CompareOperation.EQUAL, "="),
                compare(document, Operation.CompareOperation.NOT_EQUAL, "!="),
                compare(document, Operation.CompareOperation.GREAT_THAN, ">"),
                compare(document, Operation.CompareOperation.GREAT_THAN_EQUAL, ">="),
                compare(document, Operation.CompareOperation.LESS_THAN, "<"),
                compare(document, Operation.CompareOperation.LESS_THAN_EQUAL, "<="),

                in(document, Operation.CompareOperation.IN, "IN"),
                in(document, Operation.CompareOperation.NOT_IN, "NOT IN"),

                between(document, Operation.CompareOperation.BETWEEN, "BETWEEN"),
                between(document, Operation.CompareOperation.NOT_BETWEEN, "NOT BETWEEN")

        )));

        return sql;
    }

    private Element isNull(Document document, Operation operation) {
        final Element trim = document.createElement("trim");
        trim.setAttribute("suffix", " IS NULL");
        trim.appendChild(include(document, SQLConstants.SQL_CRITERION_VALUE, property(document, "value", "criterion.target")));
        return whenOrOtherwise(document, test(operation), trim);
    }

    private Element isNotNull(Document document, Operation operation) {
        final Element trim = document.createElement("trim");
        trim.setAttribute("suffix", " IS NOT NULL");
        trim.appendChild(include(document, SQLConstants.SQL_CRITERION_VALUE, property(document, "value", "criterion.target")));
        return whenOrOtherwise(document, test(operation), trim);
    }


    private Element compare(Document document, Operation operation, String operator) {
        return whenOrOtherwise(document, test(operation),
                bind(document, "value", "criterion.target"),
                include(document, SQLConstants.SQL_CRITERION_VALUE, property(document, "value", "criterion.target")),
                cdata(document, operator),
                bind(document, "value", "criterion.value"),
                include(document, SQLConstants.SQL_CRITERION_VALUE, property(document, "value", "criterion.value"))
        );
    }

    private Element in(Document document, Operation operation, String operator) {

        final Element in = document.createElement("trim");
        in.setAttribute("prefix", operator + '(');
        in.setAttribute("suffix", ")");
        in.appendChild(include(document, SQLConstants.SQL_CRITERION_VALUE, property(document, "value", "criterion.value")));

        return whenOrOtherwise(document, test(operation),
                include(document, SQLConstants.SQL_CRITERION_VALUE, property(document, "value", "criterion.target")),
                in
        );

    }

    private Element between(Document document, Operation operation, String prefix) {
        final Element between = document.createElement("trim");
        between.setAttribute("prefix", prefix);
        between.appendChild(include(document, SQLConstants.SQL_CRITERION_VALUE, property(document, "value", "criterion.min")));

        final Element and = document.createElement("trim");
        and.setAttribute("prefix", " AND ");
        and.appendChild(include(document, SQLConstants.SQL_CRITERION_VALUE, property(document, "value", "criterion.max")));

        return whenOrOtherwise(document, test(operation), between, and);
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

