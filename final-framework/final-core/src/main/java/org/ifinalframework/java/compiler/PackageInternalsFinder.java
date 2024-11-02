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

package org.ifinalframework.java.compiler;

import javax.tools.JavaFileObject;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarEntry;

/**
 * PackageInternalsFinder.
 */
public class PackageInternalsFinder {

    private static final String CLASS_FILE_EXTENSION = ".class";


    private final ClassLoader classLoader;

    public PackageInternalsFinder(final ClassLoader classLoader) {

        this.classLoader = classLoader;
    }

    public List<JavaFileObject> find(final String packageName) throws IOException {

        String javaPackageName = packageName.replace("\\.", "/");

        List<JavaFileObject> result = new ArrayList<>();

        Enumeration<URL> urlEnumeration = classLoader.getResources(javaPackageName);
        while (urlEnumeration.hasMoreElements()) {
            // one URL for each jar on the classpath that has the given package
            URL packageFolderURL = urlEnumeration.nextElement();
            result.addAll(listUnder(packageName, packageFolderURL));
        }

        return result;
    }

    private Collection<JavaFileObject> listUnder(final String packageName, final URL packageFolderURL) {

        File directory = new File(packageFolderURL.getFile());
        if (directory.isDirectory()) {
            // browse local .class files - useful for local execution
            return processDir(packageName, directory);
        } else {
            // browse a jar file
            return processJar(packageFolderURL);
        }
        // maybe there can be something else for more involved class loaders
    }

    private List<JavaFileObject> processJar(final URL packageFolderURL) {

        List<JavaFileObject> result = new ArrayList<>();
        try {
            String jarUri = packageFolderURL.toExternalForm()
                    .substring(0, packageFolderURL.toExternalForm().lastIndexOf("!/"));

            JarURLConnection jarConn = (JarURLConnection) packageFolderURL.openConnection();
            String rootEntryName = jarConn.getEntryName();
            int rootEnd = rootEntryName.length() + 1;

            Enumeration<JarEntry> entryEnum = jarConn.getJarFile().entries();
            while (entryEnum.hasMoreElements()) {
                JarEntry jarEntry = entryEnum.nextElement();
                String name = jarEntry.getName();
                if (name.startsWith(rootEntryName) && name.indexOf('/', rootEnd) == -1
                        && name.endsWith(CLASS_FILE_EXTENSION)) {
                    URI uri = URI.create(jarUri + "!/" + name);
                    String binaryName = name.replace("/", ".");
                    binaryName = binaryName.replace(CLASS_FILE_EXTENSION + "$", "");

                    result.add(new CustomJavaFileObject(binaryName, uri));
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException("Wasn't able to open " + packageFolderURL + " as a jar file", e);
        }
        return result;
    }

    private List<JavaFileObject> processDir(final String packageName, final File directory) {

        List<JavaFileObject> result = new ArrayList<>();
        File[] childFiles = directory.listFiles();

        if (Objects.isNull(childFiles)) {
            return result;
        }

        for (File childFile : childFiles) {

            if (!childFile.isFile() || !childFile.getName().endsWith(CLASS_FILE_EXTENSION)) {
                continue;
            }

            // We only want the .class files.
            String binaryName = packageName + "." + childFile.getName();
            binaryName = binaryName.replaceAll(CLASS_FILE_EXTENSION + "$", "");

            result.add(new CustomJavaFileObject(binaryName, childFile.toURI()));
        }

        return result;
    }

}
