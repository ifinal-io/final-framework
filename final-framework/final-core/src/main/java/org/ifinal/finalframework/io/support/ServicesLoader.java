package org.ifinal.finalframework.io.support;


import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author likly
 * @version 1.0.0
 * @see java.util.ServiceLoader
 * @since 1.0.0
 */
public final class ServicesLoader {
    private static final Map<ClassLoader, MultiValueMap<String, String>> cache = new ConcurrentReferenceHashMap<>();

    private static final String META_INF = "META-INF";
    private static final String DELIMITER = "/";
    private static final String DEFAULT_SERVICES_PATH = "services";


    private ServicesLoader() {
    }

    public static List<String> load(@NonNull Class<?> service) {
        return load(service.getCanonicalName());
    }

    public static List<String> load(@NonNull Class<?> service, @NonNull ClassLoader classLoader) {
        return load(service.getCanonicalName(), classLoader);
    }

    public static List<String> load(@NonNull String service) {
        return load(service, String.join(DELIMITER, META_INF, DEFAULT_SERVICES_PATH, service));
    }

    public static List<String> load(@NonNull String service, @NonNull ClassLoader classLoader) {
        return load(service, classLoader, String.join(DELIMITER, META_INF, DEFAULT_SERVICES_PATH, service));
    }

    public static List<String> load(@NonNull String service, @NonNull String serviceResourceLocation) {
        return load(service, null, serviceResourceLocation);
    }

    public static List<String> load(@NonNull String service, @Nullable ClassLoader classLoader, @NonNull String propertiesResourceLocation) {
        return loadServices(service, classLoader, propertiesResourceLocation);
    }

    private static List<String> loadServices(@NonNull String service, @Nullable ClassLoader classLoader, String propertiesResourceLocation) {
        final MultiValueMap<String, String> result = cache.computeIfAbsent(classLoader, key -> new LinkedMultiValueMap<>());

        return result.computeIfAbsent(service, key -> {
            final List<String> services = new ArrayList<>();

            try {
                Enumeration<URL> urls = (classLoader != null ?
                        classLoader.getResources(propertiesResourceLocation) :
                        ClassLoader.getSystemResources(propertiesResourceLocation));
                while (urls.hasMoreElements()) {
                    URL url = urls.nextElement();
                    services.addAll(readFromResource(new UrlResource(url)));
                }
                cache.put(classLoader, result);
            } catch (IOException ex) {
                throw new IllegalArgumentException("Unable to load factories from location [" +
                        propertiesResourceLocation + "]", ex);
            }

            return new ArrayList<>(new HashSet<>(services));
        });

    }

    private static List<String> readFromResource(Resource resource) throws IOException {
        final List<String> services = new ArrayList<>();
        try (BufferedReader r = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = r.readLine()) != null) {
                int commentStart = line.indexOf('#');
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

