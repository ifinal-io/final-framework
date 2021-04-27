/*
 * Copyright 2020-2021 the original author or authors.
 *
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

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import org.ifinalframework.util.Classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

/**
 * Services loader like {@link java.util.ServiceLoader}.
 *
 * @author likly
 * @version 1.0.0
 * @see java.util.ServiceLoader
 * @since 1.0.0
 */
@Slf4j
public final class ServicesLoader {

    private static final Map<ClassLoader, MultiValueMap<String, String>> cache = new ConcurrentReferenceHashMap<>();

    private static final String META_INF = "META-INF";

    private static final String DELIMITER = "/";

    private static final String DEFAULT_SERVICES_PATH = "services";

    private ServicesLoader() {
    }

    public static List<String> load(final @NonNull Class<?> service) {
        return load(service.getCanonicalName());
    }

    public static List<String> load(final @NonNull Class<?> service, final @NonNull ClassLoader classLoader) {
        return load(service.getCanonicalName(), classLoader);
    }

    public static List<String> load(final @NonNull String service) {
        return load(service, String.join(DELIMITER, META_INF, DEFAULT_SERVICES_PATH, service));
    }

    public static List<String> load(final @NonNull String service, final @NonNull ClassLoader classLoader) {
        return load(service, classLoader, String.join(DELIMITER, META_INF, DEFAULT_SERVICES_PATH, service));
    }

    public static List<String> load(final @NonNull String service, final @NonNull String serviceResourceLocation) {
        return load(service, null, serviceResourceLocation);
    }

    public static List<String> load(final @NonNull String service, final @Nullable ClassLoader classLoader,
        final @NonNull String propertiesResourceLocation) {
        return loadServices(service, classLoader, propertiesResourceLocation);
    }

    public static List<Class<?>> loadClasses(final @NonNull Class<?> service) {
        return loadClasses(service.getCanonicalName());
    }

    public static List<Class<?>> loadClasses(final @NonNull Class<?> service, final @NonNull ClassLoader classLoader) {
        return loadClasses(service.getCanonicalName(), classLoader);
    }

    public static List<Class<?>> loadClasses(final @NonNull String service) {
        return loadClasses(service, String.join(DELIMITER, META_INF, DEFAULT_SERVICES_PATH, service));
    }

    public static List<Class<?>> loadClasses(final @NonNull String service, final @NonNull ClassLoader classLoader) {
        return loadClasses(service, classLoader, String.join(DELIMITER, META_INF, DEFAULT_SERVICES_PATH, service));
    }

    public static List<Class<?>> loadClasses(final @NonNull String service,
        final @NonNull String serviceResourceLocation) {
        return loadClasses(service, null, serviceResourceLocation);
    }

    public static List<Class<?>> loadClasses(final @NonNull String service, final @Nullable ClassLoader classLoader,
        final @NonNull String propertiesResourceLocation) {
        return load(service, classLoader, propertiesResourceLocation).stream()
            .map(name -> Classes.requiredForName(name, classLoader))
            .collect(Collectors.toList());
    }

    private static List<String> loadServices(final @NonNull String service, final @Nullable ClassLoader classLoader,
        final String propertiesResourceLocation) {

        final MultiValueMap<String, String> result = cache
            .computeIfAbsent(classLoader, key -> new LinkedMultiValueMap<>());

        return result.computeIfAbsent(service, key -> {
            final List<String> services = new ArrayList<>();

            try {
                final Enumeration<URL> urls =
                    classLoader != null ? classLoader.getResources(propertiesResourceLocation)
                        : ClassLoader.getSystemResources(propertiesResourceLocation);
                while (urls.hasMoreElements()) {
                    final URL url = urls.nextElement();
                    services.addAll(readFromResource(new UrlResource(url)));
                }
            } catch (IOException ex) {
                throw new IllegalArgumentException(
                    "Unable to load factories from location [" + propertiesResourceLocation + "]", ex);
            }

            return new ArrayList<>(new HashSet<>(services));
        });

    }

    private static List<String> readFromResource(final Resource resource) throws IOException {

        final List<String> services = new ArrayList<>();
        try (BufferedReader r = new BufferedReader(
            new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = r.readLine()) != null) {
                final int commentStart = line.indexOf('#');
                if (commentStart >= 0) {
                    line = line.substring(0, commentStart);
                }
                line = line.trim();
                if (!line.isEmpty()) {
                    services.add(line);
                }
            }
        }

        return services;

    }

}

