package org.finalframework.data.query.criteriable;

import org.finalframework.data.query.criterion.CriterionTarget;
import org.finalframework.data.query.criterion.function.CriterionFunction;
import org.finalframework.data.query.criterion.function.DateFunction;
import org.finalframework.data.query.criterion.function.JsonFunction;
import org.finalframework.data.query.criterion.function.LogicFunction;
import org.finalframework.data.query.criterion.function.MathFunction;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 23:09:27
 * @since 1.0
 */
public interface ExecuteCriteriable<V, R> extends
    DateFunction<CriterionTarget<CriterionFunction>>,
    MathFunction<V, CriterionTarget<CriterionFunction>>,
    LogicFunction<V, CriterionTarget<CriterionFunction>>,
    JsonFunction<V, CriterionTarget<CriterionFunction>> {

}
