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

package org.ifinalframework.java;

import java.util.HashMap;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;

import org.benf.cfr.reader.apiunreleased.ClassFileSource2;
import org.benf.cfr.reader.entities.ClassFile;
import org.benf.cfr.reader.entities.Method;
import org.benf.cfr.reader.mapping.MappingFactory;
import org.benf.cfr.reader.mapping.ObfuscationMapping;
import org.benf.cfr.reader.state.ClassFileSourceImpl;
import org.benf.cfr.reader.state.DCCommonState;
import org.benf.cfr.reader.util.bytestream.BaseByteData;
import org.benf.cfr.reader.util.getopt.Options;
import org.benf.cfr.reader.util.getopt.OptionsImpl;
import org.benf.cfr.reader.util.output.Dumper;
import org.benf.cfr.reader.util.output.DumperFactory;
import org.benf.cfr.reader.util.output.InternalDumperFactoryImpl;
import org.benf.cfr.reader.util.output.ToStringDumper;

/**
 * JadDriver.
 *
 * @author ilikly
 * @version 1.3.0
 * @since 1.3.0
 */
public class JadDriver {

    private Options options;
    private DCCommonState dcCommonState;
    private DumperFactory dumperFactory;

    public JadDriver() {
        HashMap<String, String> options = new HashMap<>();
        options.put("showversion", "false");
        options.put("hideutf", "false");
        this.options = new OptionsImpl(options);
        ClassFileSource2 classFileSource = new ClassFileSourceImpl(this.options);
        dcCommonState = new DCCommonState(this.options, classFileSource);
        ObfuscationMapping mapping = MappingFactory.get(this.options, dcCommonState);
        dcCommonState = new DCCommonState(dcCommonState, mapping);
        dumperFactory = new InternalDumperFactoryImpl(this.options);
    }

    public String jad(@NonNull Class<?> clazz) {
        return jad(clazz, null);
    }

    public String jad(@NonNull final Class<?> clazz, @Nullable final String methodName) {
        byte[] bytes = DumpDriver.dump(clazz);
        return jad(clazz, bytes, methodName);
    }

    public String jad(String clazz, byte[] bytes, String methodName) throws ClassNotFoundException {
        return jad(ClassUtils.forName(clazz,getClass().getClassLoader()),bytes,methodName);
    }

    public String jad(Class<?> clazz, byte[] bytes,String methodName){
        return jad(new ClassFile(new BaseByteData(bytes),clazz.getName(),dcCommonState),methodName).replace(clazz.getName(),clazz.getSimpleName());
    }

    private String jad(ClassFile c, String methodName) {

        Dumper d = new ToStringDumper(); // sentinel dumper.
        try {
            if (methodName == null) {
                c.dump(d);
            } else {
                try {
                    for (Method method : c.getMethodByName(methodName)) {
                        method.dump(d, true);
                    }
                } catch (NoSuchMethodException e) {
                    throw new IllegalArgumentException("No such method '" + methodName + "'.");
                }
            }
            d.print("");
            return d.toString();
        } finally {
            d.close();
        }
    }

}
