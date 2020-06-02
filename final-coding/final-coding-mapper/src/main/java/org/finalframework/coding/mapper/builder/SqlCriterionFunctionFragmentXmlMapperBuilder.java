package org.finalframework.coding.mapper.builder;


import java.util.Arrays;
import org.finalframework.coding.entity.Entity;
import org.finalframework.coding.mapper.SQLConstants;
import org.finalframework.coding.mapper.TypeHandlers;
import org.finalframework.data.query.operation.DateOperation;
import org.finalframework.data.query.operation.StringOperation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author likly
 * @version 1.0
 * @date 2020-06-01 14:20:33
 * @since 1.0
 */
public class SqlCriterionFunctionFragmentXmlMapperBuilder extends AbsSqlFragmentXmlMapperBuilder {
    private final Integer loop;

    public SqlCriterionFunctionFragmentXmlMapperBuilder(TypeHandlers typeHandlers, Integer loop) {
        super(typeHandlers);
        this.loop = loop;
    }

    @Override
    protected Element buildSqlFragment(Document document, Entity entity) {
        final Element sql = sql(document, id());
        sql.appendChild(choose(document, Arrays.asList(
            whenOrOtherwise(document, String.format("'%s' == ${function}.name()", "NOW"), cdata(document, "NOW()")),
            whenOrOtherwise(document, String.format("'%s' == ${function}.name()", DateOperation.DATE), date(document)),
            whenOrOtherwise(document, String.format("'%s' == ${function}.name()", StringOperation.CONCAT),
                concat(document))
        )));
        return sql;
    }

    private Element date(Document document) {
        final Element trim = document.createElement("trim");
        trim.setAttribute("prefix", "DATE(");
        trim.setAttribute("suffix", ")");
        trim.appendChild(include(document, getValueRefId(), property(document, "value", "${function}.value")));
        return trim;
    }

    private Element concat(Document document) {
        final Element trim = document.createElement("trim");
        trim.setAttribute("prefix", "CONCAT(");
        trim.setAttribute("suffix", ")");
        trim.appendChild(include(document, getValueRefId(), property(document, "value", "${function}.value")));
        return trim;
    }

    private String getValueRefId() {
        return SQLConstants.SQL_CRITERION_VALUE + "-" + (loop + 1);
    }

    @Override
    public String id() {
        return SQLConstants.SQL_CRITERION_FUNCTION + (loop == 0 ? "" : "-" + loop);
    }
}

