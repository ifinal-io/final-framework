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


import org.finalframework.coding.file.JavaSource;
import org.finalframework.core.configuration.Configuration;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import java.io.IOException;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-27 22:19:24
 * @since 1.0
 */
public abstract class JavaSourceGenerator<T extends JavaSource> extends TemplateCodeGenerator {
    protected final PackageNameGenerator packageNameGenerator;

    public JavaSourceGenerator(ProcessingEnvironment processEnv, String targetRoute) {
        super(processEnv, targetRoute);
        String entity = Configuration.getInstance().getString("final.coding.domain.entity", "entity");
        this.packageNameGenerator = new DefaultPackageNameGenerator(processEnv, entity, targetRoute);
    }

    @Override
    public Void generate(TypeElement typeElement) {
        coding(buildJavaSource(typeElement));
        return null;
    }

    protected abstract T buildJavaSource(TypeElement entity);


    protected void coding(T source) {
        try {
            TypeElement mapperElement = processEnv.getElementUtils().getTypeElement(source.getName());
            if (mapperElement == null) {
                coder.coding(source, processEnv.getFiler().createSourceFile(source.getName()).openWriter());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

