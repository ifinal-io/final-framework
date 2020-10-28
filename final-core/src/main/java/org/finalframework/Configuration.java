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
package org.finalframework;

import org.springframework.core.io.ClassPathResource;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.util.Properties;
import java.util.function.BiConsumer;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-15 19:36:13
 * @since 1.0
 */
public class Configuration {
    private static final String PROPERTIES_PATH = "final.properties";
    private static Configuration INSTANCE = new Configuration();

    private final Properties properties = new Properties();
    private boolean isConfiguration;

    private Configuration() {
        ClassPathResource resource = new ClassPathResource("/" + PROPERTIES_PATH);
        if (resource.exists()) {
            try {
                properties.load(resource.getInputStream());
                isConfiguration = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            isConfiguration = false;
        }
    }

    public static Configuration getInstance() {
        return INSTANCE;
    }

    public static void main(String[] args) {
        Configuration configuration = Configuration.getInstance();
        Properties properties = configuration.properties;
        properties.forEach(new BiConsumer<Object, Object>() {
            @Override
            public void accept(Object key, Object value) {
                System.out.println(String.format("key=%s,value=%s", key, value));
            }
        });

    }

    public boolean isConfiguration() {
        return isConfiguration;
    }

    public String getString(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Java 编译时加载配置文件
     *
     * @param processingEnv 编译环境
     */
    public void load(ProcessingEnvironment processingEnv) {
        try {
            FileObject resource = processingEnv.getFiler().getResource(StandardLocation.CLASS_OUTPUT, "", PROPERTIES_PATH);
            properties.load(resource.openInputStream());
        } catch (Exception e) {
            // ignore
        }
    }


}
