package org.finalframework.coding.mapper.builder;


import org.finalframework.coding.entity.Entity;
import org.finalframework.coding.entity.Property;
import org.finalframework.data.query.enums.AndOr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Arrays;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-28 10:44:29
 * @since 1.0
 */
public class SqlCriteriaCriterionFragmentXmlMapperBuilder extends AbsSqlFragmentXmlMapperBuilder {

    @Override
    public String id() {
        return SQL_CRITERIA_CRITERION;
    }

    @Override
    protected Element buildSqlFragment(Document document, Entity<Property> entity) {
        Element sql = sql(document, id());
        Element choose = choose(document, Arrays.asList(
                whenOrOtherwise(document, "criteria.andOr.name() == 'AND'", foreach(document, AndOr.AND)),
                whenOrOtherwise(document, "criteria.andOr.name() == 'OR'", foreach(document, AndOr.OR))
        ));
        sql.appendChild(choose);
        return sql;
    }

    private Element foreach(Document document, AndOr andOr) {
        Element foreach = document.createElement("foreach");
        foreach.setAttribute("collection", "criteria.criterion");
        foreach.setAttribute("item", "criterion");
        foreach.setAttribute("open", "(");
        foreach.setAttribute("separator", String.format(" %s ", andOr.name()));
        foreach.setAttribute("close", ")");
        foreach.appendChild(include(document, SQL_CRITERION));
        return foreach;
    }

}

