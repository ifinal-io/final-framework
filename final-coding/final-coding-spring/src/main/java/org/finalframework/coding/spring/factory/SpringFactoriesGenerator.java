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

package org.finalframework.coding.spring.factory;


import org.finalframework.coding.Coder;
import org.finalframework.core.generator.Generator;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.StringUtils;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-06 09:47:00
 * @since 1.0
 */
public class SpringFactoriesGenerator implements Generator<SpringFactories, Void> {

    private static final String RESOURCE_FILE = "META-INF/spring.factories";
    private final Coder coder = Coder.getDefaultCoder();
    private final ProcessingEnvironment processingEnv;
    private final Filer filer;


    public SpringFactoriesGenerator(ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
        this.filer = processingEnv.getFiler();
    }


    @Override
    public Void generate(SpringFactories springFactories) {

        try {
            // would like to be able to print the full path
            // before we attempt to get the resource in case the behavior
            // of filer.getResource does change to match the spec, but there's
            // no good way to resolve CLASS_OUTPUT without first getting a resource.
            FileObject existingFile = filer.getResource(StandardLocation.CLASS_OUTPUT, "",
                    RESOURCE_FILE);
            InputStreamResource resource = new InputStreamResource(existingFile.openInputStream());
            Properties properties = PropertiesLoaderUtils.loadProperties(resource);
            for (Map.Entry<?, ?> entry : properties.entrySet()) {
                String factoryClassName = ((String) entry.getKey()).trim();
                for (String factoryName : StringUtils.commaDelimitedListToStringArray((String) entry.getValue())) {
                    springFactories.addSpringFactory(factoryClassName, factoryName.trim());
                }
            }

            info("Looking for existing resource file at " + existingFile.toUri());
        } catch (IOException e) {
            // According to the javadoc, Filer.getResource throws an exception
            // if the file doesn't already exist.  In practice this doesn't
            // appear to be the case.  Filer.getResource will happily return a
            // FileObject that refers to a non-existent file but will throw
            // IOException if you try to open an input stream for it.
            info("Resource file did not already exist.");
        }

        if (springFactories.getSpringFactories().isEmpty()) return null;

        try {
            FileObject fileObject = filer.createResource(StandardLocation.CLASS_OUTPUT, "", RESOURCE_FILE);
            coder.coding(springFactories, fileObject.openWriter());
            info("Create spring.factories :" + springFactories);
        } catch (Exception e) {
//            logger.error("Create spring.factories error :", e);
            error("Create spring.factories error :" + springFactories + ",\n" + e.getMessage());
        }

        return null;
    }


    private void info(String msg) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, msg);
    }

    private void error(String msg) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, msg);
    }
}

