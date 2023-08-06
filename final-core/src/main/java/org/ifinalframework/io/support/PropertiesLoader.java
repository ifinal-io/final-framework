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

package org.ifinalframework.io.support;

import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author ilikly
 * @version 1.0.0
 * @see org.springframework.core.io.support.SpringFactoriesLoader
 * @since 1.0.0
 */
public final class PropertiesLoader {

    private static final Map<ClassLoader, MultiValueMap<String, String>> cache = new ConcurrentReferenceHashMap<>();

    private PropertiesLoader() {
    }

    public static List<String> loadPropertyValues(final @NonNull String propertyKey,
                                                  final @NonNull String propertiesResourceLocation) {

        return loadPropertyValues(propertyKey, null, propertiesResourceLocation);
    }

    public static List<String> loadPropertyValues(final @NonNull String propertyKey,
                                                  final @Nullable ClassLoader classLoader,
                                                  final @NonNull String propertiesResourceLocation) {

        return loadPropertyValues(classLoader, propertiesResourceLocation)
                .getOrDefault(propertyKey, Collections.emptyList());
    }

    private static Map<String, List<String>> loadPropertyValues(final @Nullable ClassLoader classLoader,
                                                                final String propertiesResourceLocation) {

        MultiValueMap<String, String> result = cache.get(classLoader);
        if (result != null) {
            return result;
        }

        try {
            final Enumeration<URL> urls = classLoader != null
                    ? classLoader.getResources(propertiesResourceLocation)
                    : ClassLoader.getSystemResources(propertiesResourceLocation);
            result = new LinkedMultiValueMap<>();
            while (urls.hasMoreElements()) {
                final URL url = urls.nextElement();
                final UrlResource resource = new UrlResource(url);
                final Properties properties = PropertiesLoaderUtils.loadProperties(resource);
                for (Map.Entry<?, ?> entry : properties.entrySet()) {
                    final String factoryClassName = ((String) entry.getKey()).trim();
                    for (String factoryName : StringUtils.commaDelimitedListToStringArray((String) entry.getValue())) {
                        result.add(factoryClassName, factoryName.trim());
                    }
                }
            }
            cache.put(classLoader, result);
            return result;
        } catch (IOException ex) {
            throw new IllegalArgumentException(
                    "Unable to load factories from location [" + propertiesResourceLocation + "]", ex);
        }
    }

}

