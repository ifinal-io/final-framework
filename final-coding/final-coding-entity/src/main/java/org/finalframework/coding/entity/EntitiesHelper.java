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


import org.finalframework.coding.Coder;
import org.finalframework.core.Assert;
import org.finalframework.data.annotation.IEntity;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.StandardLocation;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 将实现了{@link IEntity}接口的JavaBean对象生成到{@link EntitiesHelper#RESOURCE_FILE}文件中，以供其他处理使用。
 * 如编译时生成{@link org.finalframework.data.query.QEntity}等。
 *
 * @author likly
 * @version 1.0
 * @date 2019-11-04 13:11:45
 * @since 1.0
 */
public class EntitiesHelper {

    /**
     * 生成的文件路径: classpath:/META-INF/final.entities
     */
    private static final String RESOURCE_FILE = "META-INF/final.entities";
    private final Coder coder = Coder.getDefaultCoder();
    private final ProcessingEnvironment processingEnv;

    public EntitiesHelper(ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
    }

    public Entities parse() {
        Entities.Builder builder = Entities.builder();
        try {
            InputStream resourceAsStream = getClass().getResourceAsStream("/" + RESOURCE_FILE);
            BufferedReader reader = new BufferedReader(new InputStreamReader(resourceAsStream));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.addEntity(processingEnv.getElementUtils().getTypeElement(line.trim()));
            }
            reader.close();
        } catch (Exception e) {
            //throw new RuntimeException(e);
        }
        return builder.build();
    }

    public void generate(Entities entities) {
        if (Assert.isEmpty(entities.getEntities())) return;
        try {
            coder.coding(entities, processingEnv.getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", RESOURCE_FILE).openWriter());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

