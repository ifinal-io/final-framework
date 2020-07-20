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

package org.finalframework.data.query.criterion;


import org.finalframework.data.annotation.query.Criterion;
import org.finalframework.data.annotation.query.CriterionHandler;
import org.finalframework.data.annotation.query.MeteData;
import org.finalframework.data.util.Velocities;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2020-07-20 12:57:02
 * @since 1.0
 */
public class VelocityCriterionHandler implements CriterionHandler {
    @Override
    public String handle(Criterion criterion, MeteData meteData) {
        final String value = Arrays.stream(criterion.value()).map(String::trim).collect(Collectors.joining());
        return Velocities.getValue(value, meteData);
    }
}

