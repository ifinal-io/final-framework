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

package org.finalframework.coding.mapper;


import org.finalframework.coding.entity.EntityFactory;
import org.finalframework.coding.generator.JavaSourceGenerator;
import org.finalframework.core.configuration.Configuration;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-28 09:30:39
 * @since 1.0
 */
public class MapperGenerator extends JavaSourceGenerator<Mapper> {

    private static final String MAPPER_PACKAGE_PATH = "dao.mapper";

    private static final String MAPPER_PREFIX = "Abs";
    private static final String MAPPER_SUFFIX = "Mapper";

    public MapperGenerator(ProcessingEnvironment processEnv) {
        super(processEnv, Configuration.getInstance().getString("final.coding.mapper.path", MAPPER_PACKAGE_PATH));
    }

    @Override
    protected Mapper buildJavaSource(TypeElement typeElement) {
        final String packageName = packageNameGenerator.generate(typeElement);
        String mapperName = typeElement.getSimpleName().toString() + MAPPER_SUFFIX;
        String absMapperName = MAPPER_PREFIX + mapperName;

        String mapperClassName = packageName + "." + mapperName;

        TypeElement mapperTypeElement = processEnv.getElementUtils().getTypeElement(mapperClassName);
//        boolean inner = mapperTypeElement != null;
        boolean inner = false;
        return Mapper.builder()
                .packageName(packageName)
                .simpleName(inner ? absMapperName : mapperName)
                .inner(inner)
                .entity(EntityFactory.create(processEnv, typeElement))
                .build();
    }
}

