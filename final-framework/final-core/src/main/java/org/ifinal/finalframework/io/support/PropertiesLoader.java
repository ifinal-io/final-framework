package org.ifinal.finalframework.io.support;


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
 * @author likly
 * @version 1.0.0
 * @see org.springframework.core.io.support.SpringFactoriesLoader
 * @since 1.0.0
 */
public final class PropertiesLoader {

    private static final Map<ClassLoader, MultiValueMap<String, String>> cache = new ConcurrentReferenceHashMap<>();

    private PropertiesLoader() {
    }

    public static List<String> loadPropertyValues(final @NonNull String propertyKey, final @NonNull String propertiesResourceLocation) {

        return loadPropertyValues(propertyKey, null, propertiesResourceLocation);
    }

    public static List<String> loadPropertyValues(final @NonNull String propertyKey, final @Nullable ClassLoader classLoader, final @NonNull String propertiesResourceLocation) {

        return loadPropertyValues(classLoader, propertiesResourceLocation).getOrDefault(propertyKey, Collections.emptyList());
    }

    private static Map<String, List<String>> loadPropertyValues(final @Nullable ClassLoader classLoader, final String propertiesResourceLocation) {

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

