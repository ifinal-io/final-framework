package org.finalframework.core.io.support;


import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-21 17:31:07
 * @since 1.0
 */
public final class FinalFactoriesLoader {
    private static final String FACTORIES_RESOURCE_LOCATION = "META/final.factories";

    public static List<String> loadPropertyValues(Class<?> factoryClass) {
        return loadPropertyValues(factoryClass, null, FACTORIES_RESOURCE_LOCATION);
    }

    public static List<String> loadPropertyValues(@NonNull Class<?> factoryClass, @Nullable ClassLoader classLoader, @NonNull String propertiesResourceLocation) {
        final String factoryClassName = factoryClass.getName();
        return PropertiesLoader.loadPropertyValues(factoryClassName, classLoader, propertiesResourceLocation);
    }

}

