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

import javax.lang.model.element.Modifier;
import javax.lang.model.element.NestingKind;
import javax.tools.JavaFileObject;
import java.io.*;
import java.net.URI;

/**
 * CustomJavaFileObject.
 */
public class CustomJavaFileObject implements JavaFileObject {


    private final String binaryName;

    private final URI uri;

    private final String name;

    public CustomJavaFileObject(final String binaryName, final URI uri) {

        this.uri = uri;
        this.binaryName = binaryName;
        // for FS based URI the path is not null, for JAR URI the scheme specific part is not null
        name = uri.getPath() == null ? uri.getSchemeSpecificPart() : uri.getPath();
    }

    public URI toUri() {
        return uri;
    }

    public InputStream openInputStream() throws IOException {
        return uri.toURL().openStream();
    }

    public OutputStream openOutputStream() {
        throw new UnsupportedOperationException();
    }

    public String getName() {
        return name;
    }

    public Reader openReader(final boolean ignoreEncodingErrors) {

        throw new UnsupportedOperationException();
    }

    public CharSequence getCharContent(final boolean ignoreEncodingErrors) {

        throw new UnsupportedOperationException();
    }

    public Writer openWriter() throws IOException {
        throw new UnsupportedOperationException();
    }

    public long getLastModified() {
        return 0;
    }

    public boolean delete() {
        throw new UnsupportedOperationException();
    }

    public Kind getKind() {
        return Kind.CLASS;
    }

    public boolean isNameCompatible(final String simpleName, final Kind kind) {

        String baseName = simpleName + kind.extension;
        return kind.equals(getKind())
            && (baseName.equals(getName())
            || getName().endsWith("/" + baseName));
    }

    public NestingKind getNestingKind() {
        throw new UnsupportedOperationException();
    }

    public Modifier getAccessLevel() {
        throw new UnsupportedOperationException();
    }

    public String binaryName() {
        return binaryName;
    }

    public String toString() {
        return this.getClass().getName() + "[" + this.toUri() + "]";
    }
}

