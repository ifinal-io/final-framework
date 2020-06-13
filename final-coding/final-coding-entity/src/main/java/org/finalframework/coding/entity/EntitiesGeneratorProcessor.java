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

package org.finalframework.coding.entity;

import com.google.auto.service.AutoService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import org.finalframework.coding.Coder;
import org.finalframework.coding.entity.validator.EntityValidator;
import org.finalframework.coding.entity.validator.EnumValidator;
import org.finalframework.core.configuration.Configuration;
import org.finalframework.data.annotation.IEntity;
import org.finalframework.data.annotation.IEnum;
import org.finalframework.data.annotation.Transient;
import org.finalframework.data.mapping.converter.NameConverterRegistry;

/**
 * QEntity 代码生成处理器
 *
 * @author likly
 * @version 1.0
 * @date 2018-10-19 15:14
 * @since 1.0
 */
@SuppressWarnings("unused")
@AutoService(Processor.class)
@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class EntitiesGeneratorProcessor extends AbstractProcessor {

    private final Coder coder = Coder.getDefaultCoder();
    private final Set<TypeElement> entities = new HashSet<>(128);
    private EntityFilter entityFilter;
    private EntitiesHelper entitiesHelper;

    private final List<ElementVisitor<?, ?>> elementVisitors = new ArrayList<>(8);

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        Configuration.getInstance().load(processingEnv);
        NameConverterRegistry.getInstance().reload();
        this.entityFilter = new EntityFilter(processingEnv);
        this.entitiesHelper = new EntitiesHelper(processingEnv);

        this.elementVisitors.add(new EnumValidator(processingEnv, IEnum.class));
        this.elementVisitors.add(new EntityValidator(processingEnv, IEntity.class));
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        roundEnv.getRootElements().forEach(it -> {
            elementVisitors.forEach(elementVisitor -> it.accept(elementVisitor, null));
        });

        if (roundEnv.processingOver()) {
            coding(Entities.builder().addEntities(entities).build());
        } else {
            ElementFilter.typesIn(roundEnv.getRootElements())
                    .stream()
                    .filter(it -> it.getAnnotation(Transient.class) == null)
                    .filter(entityFilter::matches)
                    .forEach(entities::add);
        }
        return false;
    }

    private void coding(Entities entities) {
        entitiesHelper.generate(entities);
    }


}
