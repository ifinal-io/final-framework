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

package org.finalframework.coding.query;

import org.finalframework.core.configuration.Configuration;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-21 18:05:44
 * @since 1.0
 */

public class CriteriaGenerator extends AbsEntityGenerator<Criteria> {

    private static final String QUERY_PACKAGE_PATH = "dao.query";

    public CriteriaGenerator(ProcessingEnvironment processEnv) {
        super(processEnv, Configuration.getInstance().getString("final.coding.query.path", QUERY_PACKAGE_PATH));
    }

    @Override
    protected Criteria buildEntityJavaSource(TypeElement typeElement, QEntity entity) {
        return Criteria.builder().packageName(entity.getPackageName())
                .simpleName(typeElement.getSimpleName() + "Criteria")
                .entity(entity)
                .build();
    }
}

