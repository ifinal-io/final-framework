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

package org.finalframework.core.io.support;


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
import java.util.*;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-21 17:16:52
 * @see org.springframework.core.io.support.SpringFactoriesLoader
 * @since 1.0
 */
public final class PropertiesLoader {

    private static final Map<ClassLoader, MultiValueMap<String, String>> cache = new ConcurrentReferenceHashMap<>();

    public static List<String> loadPropertyValues(@NonNull String propertyKey, @NonNull String propertiesResourceLocation) {
        return loadPropertyValues(propertyKey, null, propertiesResourceLocation);
    }

    public static List<String> loadPropertyValues(@NonNull String propertyKey, @Nullable ClassLoader classLoader, @NonNull String propertiesResourceLocation) {
        return loadPropertyValues(classLoader, propertiesResourceLocation).getOrDefault(propertyKey, Collections.emptyList());
    }

    private static Map<String, List<String>> loadPropertyValues(@Nullable ClassLoader classLoader, String propertiesResourceLocation) {
        MultiValueMap<String, String> result = cache.get(classLoader);
        if (result != null) {
            return result;
        }

        try {
            Enumeration<URL> urls = (classLoader != null ?
                    classLoader.getResources(propertiesResourceLocation) :
                    ClassLoader.getSystemResources(propertiesResourceLocation));
            result = new LinkedMultiValueMap<>();
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                UrlResource resource = new UrlResource(url);
                Properties properties = PropertiesLoaderUtils.loadProperties(resource);
                for (Map.Entry<?, ?> entry : properties.entrySet()) {
                    String factoryClassName = ((String) entry.getKey()).trim();
                    for (String factoryName : StringUtils.commaDelimitedListToStringArray((String) entry.getValue())) {
                        result.add(factoryClassName, factoryName.trim());
                    }
                }
            }
            cache.put(classLoader, result);
            return result;
        } catch (IOException ex) {
            throw new IllegalArgumentException("Unable to load factories from location [" +
                    propertiesResourceLocation + "]", ex);
        }
    }
}

