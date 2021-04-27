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

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * DynamicClassLoader.
 */
public class DynamicClassLoader extends ClassLoader {

    private final Map<String, BytesJavaFileObject> byteCodes = new HashMap<>();

    public DynamicClassLoader(final ClassLoader classLoader) {

        super(classLoader);
    }

    public void registerCompiledSource(final BytesJavaFileObject byteCode) {

        byteCodes.put(byteCode.getClassName(), byteCode);
    }

    @Override
    protected Class<?> findClass(final String name) throws ClassNotFoundException {

        BytesJavaFileObject byteCode = byteCodes.get(name);
        if (byteCode == null) {
            return super.findClass(name);
        }

        return super.defineClass(name, byteCode.getByteCode(), 0, byteCode.getByteCode().length);
    }

    public Map<String, Class<?>> getClasses() throws ClassNotFoundException {
        Map<String, Class<?>> classes = new HashMap<>();
        for (BytesJavaFileObject byteCode : byteCodes.values()) {
            classes.put(byteCode.getClassName(), findClass(byteCode.getClassName()));
        }
        return classes;
    }

    public Map<String, byte[]> getByteCodes() {
        Map<String, byte[]> result = new HashMap<>(byteCodes.size());
        for (Entry<String, BytesJavaFileObject> entry : byteCodes.entrySet()) {
            result.put(entry.getKey(), entry.getValue().getByteCode());
        }
        return result;
    }
}
