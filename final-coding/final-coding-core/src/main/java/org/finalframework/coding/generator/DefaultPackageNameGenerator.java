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

package org.finalframework.coding.generator;


import org.finalframework.core.Assert;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-26 23:45:10
 * @since 1.0
 */
public class DefaultPackageNameGenerator implements PackageNameGenerator {

    private final ProcessingEnvironment processEnv;
    private final String entityRoute;
    private final String targetRoute;

    public DefaultPackageNameGenerator(ProcessingEnvironment processEnv, String entityRoute, String targetRoute) {
        this.processEnv = processEnv;
        this.entityRoute = entityRoute;
        this.targetRoute = targetRoute;
    }

    @Override
    public String generate(TypeElement entity) {
        PackageElement packageElement = processEnv.getElementUtils().getPackageOf(entity);
        final String packageName = packageElement.getQualifiedName().toString();
        final String domain = calcEntityDomain(packageName);
        final String entityPath = String.format("%s.%s", domain, entityRoute);
        final String targetPath = String.format("%s.%s", domain, targetRoute);
        final String route = packageName.replace(entityPath, "");

        return Assert.isBlank(route) ? targetPath : targetPath + route;
    }

    private String calcEntityDomain(String packageName) {
        int index = packageName.indexOf(entityRoute);
        if (index != -1) {
            return packageName.substring(0, index - 1);
        }
        return null;
    }
}

