/*
 * Copyright 2020-2022 the original author or authors.
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

package org.ifinalframework.boot.env;

import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * Load {@link PropertySource}s from {@code locations} with {@link PropertySourceLoader}
 *
 * @author iimik
 * @version 1.3.1
 * @see PropertySourceLoader
 * @see ResourceLoader
 * @since 1.3.1
 */
@Slf4j
public class PropertySourceLoaders {

    private final List<PropertySourceLoader> propertySourceLoaders;
    private final ResourceLoader resourceLoader;

    public PropertySourceLoaders(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
        this.propertySourceLoaders = SpringFactoriesLoader.loadFactories(PropertySourceLoader.class, getClass().getClassLoader());
    }

    /**
     * load property sources from locations.
     *
     * @param locations property source locations.
     * @return property sources
     * @throws IOException io exception
     * @see #load(Collection)
     */
    public List<PropertySource<?>> load(String... locations) throws IOException {
        return load(Arrays.asList(locations));
    }

    /**
     * load property sources from locations.
     *
     * @param locations property source locations.
     * @return property sources
     * @throws IOException io exception
     * @see #load(String)
     */
    public List<PropertySource<?>> load(Collection<String> locations) throws IOException {
        List<PropertySource<?>> propertySources = new LinkedList<>();
        for (String location : locations) {
            propertySources.addAll(load(location));
        }
        return propertySources;
    }

    private List<PropertySource<?>> load(String location) throws IOException {

        List<PropertySource<?>> propertySources = new LinkedList<>();

        if (resourceLoader instanceof ResourcePatternResolver) {
            Resource[] resources = ((ResourcePatternResolver) resourceLoader).getResources(location);

            for (Resource resource : resources) {
                load(resource, propertySources);
            }

        } else {
            Resource resource = resourceLoader.getResource(location);
            load(resource, propertySources);
        }

        return propertySources;
    }

    private void load(Resource resource, List<PropertySource<?>> propertySources) throws IOException {
        for (PropertySourceLoader loader : propertySourceLoaders) {
            for (String fileExtension : loader.getFileExtensions()) {
                if (StringUtils.endsWithIgnoreCase(resource.getFilename(), fileExtension)) {
                    List<PropertySource<?>> sources = loader.load(resource.getFilename(), resource);
                    propertySources.addAll(sources);
                }
            }
        }
    }


}
