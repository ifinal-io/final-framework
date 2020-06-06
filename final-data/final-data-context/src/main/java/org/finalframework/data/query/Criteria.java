package org.finalframework.data.query;

import org.finalframework.core.Streamable;
import org.finalframework.data.query.criterion.Criterion;
import org.finalframework.data.query.enums.AndOr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-25 11:34
 * @since 1.0
 */
public interface Criteria extends Criterion, Streamable<Criterion>, Iterable<Criterion>, SqlNode {

    static Criteria where(Criterion... criterion) {
        return and(criterion);
    }

    static Criteria where(Collection<Criterion> criterion) {
        return and(criterion);
    }

    static Criteria and(Criterion... criterion) {
        return and(Arrays.asList(criterion));
    }

    static Criteria and(Collection<Criterion> criterion) {
        return new CriteriaImpl(AndOr.AND, criterion);
    }

    static Criteria or(Criterion... criterion) {
        return or(Arrays.asList(criterion));
    }

    static Criteria or(Collection<Criterion> criterion) {
        return new CriteriaImpl(AndOr.OR, criterion);
    }

    AndOr andOr();

    @Override
    default boolean isChain() {
        return true;
    }

    default Criteria add(Criterion... criterion) {
        return add(Arrays.asList(criterion));
    }

    Criteria add(Collection<Criterion> criterion);

    Criteria and(Criteria... criteria);

    Criteria or(Criteria... criteria);

    @Override
    default void apply(Node parent, String value) {

        final Document document = parent.getOwnerDocument();
        int index = 0;

        final Element criteria = document.createElement("trim");
        criteria.setAttribute("prefix", "(");
        criteria.setAttribute("prefixOverrides", "AND |OR ");
        criteria.setAttribute("suffix", ")");

        for (Criterion criterion : this) {
            final Element trim = document.createElement("trim");
            trim.setAttribute("prefix", String.format(" %s ", andOr().name()));
            criterion.apply(trim, String.format("%s.criteria[%d]", value, index));
            criteria.appendChild(trim);
            index++;
        }


        parent.appendChild(criteria);

    }
}
