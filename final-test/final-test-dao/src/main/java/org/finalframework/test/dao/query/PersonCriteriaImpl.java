package org.finalframework.test.dao.query;


import org.finalframework.data.query.CriteriaImpl;
import org.finalframework.data.query.Criteria;
import org.finalframework.data.query.Criterion;
import org.finalframework.data.query.enums.AndOr;
import org.finalframework.test.query.QPerson;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-27 15:33:55
 * @since 1.0
 */
public class PersonCriteriaImpl extends CriteriaImpl {
    public PersonCriteriaImpl() {
    }

    public PersonCriteriaImpl(AndOr andOr, Collection<Criterion> criterion) {
        super(andOr, criterion);
    }

    public PersonCriteriaImpl addIdEqual(Long id) {
        add(QPerson.id.eq(id));
        return this;
    }

//    @Override
//    public PersonCriteria and(PersonCriteria... criteria) {
//        return super.and(criteria);
//    }

    @Override
    public Criteria or(Criteria... criteria) {
        return super.or(criteria);
    }
}

