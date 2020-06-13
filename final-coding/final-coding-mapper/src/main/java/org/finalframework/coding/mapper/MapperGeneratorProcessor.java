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


import com.google.auto.service.AutoService;
import org.finalframework.coding.entity.Entities;
import org.finalframework.coding.entity.EntitiesHelper;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.util.Set;

/**
 * Mapper.java 生成注解处理器
 *
 * @author likly
 * @version 1.0
 * @date 2019-10-26 12:59:10
 * @since 1.0
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class MapperGeneratorProcessor extends AbstractProcessor {

    private Elements elementUtils;

    private MapperGenerator mapperGenerator;

    private boolean entitiesProcessed = false;

    private EntitiesHelper entitiesHelper;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.elementUtils = processingEnv.getElementUtils();
        this.entitiesHelper = new EntitiesHelper(processingEnv);
        this.mapperGenerator = new MapperGenerator(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        processEntities();
        return false;
    }

    private void processEntities() {

        if (entitiesProcessed) return;
        Entities entities = entitiesHelper.parse();
        for (TypeElement entity : entities) {
            mapperGenerator.generate(entity);
        }
        entitiesProcessed = true;
    }
}

