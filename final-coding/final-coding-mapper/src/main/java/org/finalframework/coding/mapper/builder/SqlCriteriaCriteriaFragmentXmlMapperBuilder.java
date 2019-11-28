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
 * @date 2019-11-28 10:59:26
 * @since 1.0
 */
public class SqlCriteriaCriteriaFragmentXmlMapperBuilder extends AbsSqlFragmentXmlMapperBuilder {

    private static final Integer MAX_CRITERIA_LOOP = 3;

    @Override
    public String id() {
        return SQL_CRITERIA_CRITERIA;
    }

    @Override
    protected Element buildSqlFragment(Document document, Entity<Property> entity) {
        Element sql = sql(document, id());
        sql.appendChild(criteria(document, 1));
        return sql;
    }

    private Element criteria(Document document, int loop) {
        return choose(document, Arrays.asList(
                whenCriteriaAndOr(document, AndOr.AND, loop),
                whenCriteriaAndOr(document, AndOr.OR, loop)
        ));
    }

    private Element whenCriteriaAndOr(Document document, AndOr andOr, int loop) {

        Element foreach = document.createElement("foreach");
        foreach.setAttribute("collection", "criteria.criteria");
        foreach.setAttribute("item", "criteria");
        foreach.setAttribute("open", "(");
        foreach.setAttribute("separator", String.format(" %s ", andOr.name()));
        foreach.setAttribute("close", ")");
        foreach.appendChild(choose(document, Arrays.asList(
                whenCriteriaIsChain(document, loop),
                whenOrOtherwise(document, null, include(document, SQL_CRITERIA_CRITERION))
        )));

        return whenOrOtherwise(document, "criteria.andOr.name == '" + andOr.name() + "'", foreach);
    }

    private Element whenCriteriaIsChain(Document document, int loop) {
        Element when = document.createElement("when");
        when.setAttribute("test", "criteria.chain");
        if (loop <= MAX_CRITERIA_LOOP) {
            when.appendChild(criteria(document, ++loop));
        }
        return when;
    }


}

