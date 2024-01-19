/*
 * Copyright 2020-2021 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ifinalframework;

import org.springframework.core.io.ClassPathResource;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import java.io.InputStream;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 * @deprecated removed future.
 */
@Slf4j
@Deprecated
public final class Configuration {

    private static final String PROPERTIES_PATH = "final.properties";

    private static final Configuration INSTANCE = new Configuration();

    private final Properties properties = new Properties();

    private boolean isConfiguration;

    private Configuration() {

        final ClassPathResource resource = new ClassPathResource("/" + PROPERTIES_PATH);
        if (resource.exists()) {
            try {
                properties.load(resource.getInputStream());
                isConfiguration = true;
            } catch (Exception e) {
                logger.error("load final.properties error: {}", e.getMessage(), e);
            }
        } else {
            isConfiguration = false;
        }
    }

    public static Configuration getInstance() {
        return INSTANCE;
    }

    public boolean isConfiguration() {
        return isConfiguration;
    }

    public String getString(final String key, final String defaultValue) {

        return properties.getProperty(key, defaultValue);
    }

    /**
     * Java 编译时加载配置文件
     *
     * @param processingEnv 编译环境
     */
    public void load(final ProcessingEnvironment processingEnv) {

        try {
            final FileObject resource = processingEnv.getFiler()
                    .getResource(StandardLocation.CLASS_OUTPUT, "", PROPERTIES_PATH);
            try (InputStream is = resource.openInputStream()) {
                properties.load(is);
            }
        } catch (Exception e) {
            //ignore
        }
    }

}
