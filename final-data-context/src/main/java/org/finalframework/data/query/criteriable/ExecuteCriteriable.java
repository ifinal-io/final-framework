/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

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
