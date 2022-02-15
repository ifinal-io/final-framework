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

package org.ifinalframework.util;

import lombok.experimental.UtilityClass;
import org.springframework.boot.SpringBootVersion;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.CodeSource;
import java.util.jar.Attributes;
import java.util.jar.JarFile;

/**
 * JarVersions.
 *
 * @author likly
 * @version 1.2.4
 * @see org.springframework.core.SpringVersion
 * @see SpringBootVersion
 * @see Package#getImplementationVersion()
 * @see Attributes.Name#IMPLEMENTATION_VERSION
 * @since 1.2.4
 */
@UtilityClass
public final class JarVersions {

    /**
     * return the version of the clazz in jar.
     *
     * @param clazz the class
     * @see Package#getImplementationVersion()
     */
    @Nullable
    public static String getVersion(@NonNull Class<?> clazz) {
        String implementationVersion = clazz.getPackage().getImplementationVersion();
        if (implementationVersion != null) {
            return implementationVersion;
        }
        CodeSource codeSource = clazz.getProtectionDomain().getCodeSource();
        if (codeSource == null) {
            return null;
        }
        URL codeSourceLocation = codeSource.getLocation();
        try {
            URLConnection connection = codeSourceLocation.openConnection();
            if (connection instanceof JarURLConnection) {
                return getImplementationVersion(((JarURLConnection) connection).getJarFile());
            }
            try (JarFile jarFile = new JarFile(new File(codeSourceLocation.toURI()))) {
                return getVersion(jarFile);
            }
        } catch (Exception ex) {
            return null;
        }
    }

    @Nullable
    private static String getVersion(@NonNull JarFile jarFile) {
        try {
            return getImplementationVersion(jarFile);
        } catch (Exception e) {
            return null;
        }
    }


    private static String getImplementationVersion(JarFile jarFile) throws IOException {
        return jarFile.getManifest().getMainAttributes().getValue(Attributes.Name.IMPLEMENTATION_VERSION);
    }
}
