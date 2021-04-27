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

package org.ifinalframework.devops.java.compiler;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;
import javax.tools.SimpleJavaFileObject;

/**
 * BytesJavaFileObject.
 */
public class BytesJavaFileObject extends SimpleJavaFileObject {

    private static final char PKG_SEPARATOR = '.';

    private static final char DIR_SEPARATOR = '/';

    private static final String CLASS_FILE_SUFFIX = ".class";

    private ByteArrayOutputStream byteArrayOutputStream;

    public BytesJavaFileObject(final String className) {

        super(URI.create("byte:///" + className.replace(PKG_SEPARATOR, DIR_SEPARATOR)
            + Kind.CLASS.extension), Kind.CLASS);
    }

    public BytesJavaFileObject(final String className, final ByteArrayOutputStream byteArrayOutputStream) {

        this(className);
        this.byteArrayOutputStream = byteArrayOutputStream;
    }

    @Override
    public OutputStream openOutputStream() {
        if (byteArrayOutputStream == null) {
            byteArrayOutputStream = new ByteArrayOutputStream();
        }
        return byteArrayOutputStream;
    }

    public byte[] getByteCode() {
        return byteArrayOutputStream.toByteArray();
    }

    public String getClassName() {
        String className = getName();
        className = className.replace(DIR_SEPARATOR, PKG_SEPARATOR);
        className = className.substring(1, className.indexOf(CLASS_FILE_SUFFIX));
        return className;
    }

}
