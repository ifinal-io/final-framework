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

package org.ifinalframework.java;

import lombok.extern.slf4j.Slf4j;
import org.benf.cfr.reader.api.CfrDriver;
import org.benf.cfr.reader.api.OutputSinkFactory;
import org.ifinalframework.util.Asserts;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.net.URL;
import java.util.*;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
public final class Decompiler {

    private Decompiler() {
    }

    public static String decompile(@NonNull Class<?> clazz) {
        return decompile(clazz, null);
    }

    public static String decompile(@NonNull final Class<?> clazz, @Nullable final String methodName) {

        Instrumentation instrumentation = Instrumentations.get();

        HashSet<Class<?>> classes = new HashSet<>(Collections.singletonList(clazz));
        URL root = ClassLoader.getSystemClassLoader().getResource(".");
        Objects.requireNonNull(root);
        ClassDumpTransformer transformer = new ClassDumpTransformer(classes, new File(root.getPath()));
        Instrumentations.retransformClasses(instrumentation, transformer, classes);
        Map<Class<?>, File> dumpResult = transformer.getDumpResult();
        File file = dumpResult.get(clazz);
        String absolutePath = file.getAbsolutePath();
        String decompile = decompile(absolutePath, methodName);
        boolean delete = file.delete();
        logger.info("delete decompile file {} {}", file.getName(), delete);
        return decompile;
    }

    public static String decompile(final String classFilePath, final String methodName) {

        return decompile(classFilePath, methodName, false);
    }

    public static String decompile(final String classFilePath, final String methodName, final boolean hideUnicode) {

        final StringBuilder result = new StringBuilder(8192);

        HashMap<String, String> options = new HashMap<>();
        options.put("showversion", "false");
        options.put("hideutf", String.valueOf(hideUnicode));
        if (Asserts.nonBlank(methodName)) {
            options.put("methodname", methodName);
        }

        OutputSinkFactory mySink = new OutputSinkFactory() {

            @Override
            public List<SinkClass> getSupportedSinks(final SinkType sinkType, final Collection<SinkClass> collection) {

                return Arrays.asList(SinkClass.STRING, SinkClass.DECOMPILED, SinkClass.DECOMPILED_MULTIVER,
                        SinkClass.EXCEPTION_MESSAGE);
            }

            @Override
            public <T> Sink<T> getSink(final SinkType sinkType, final SinkClass sinkClass) {

                return sinkable -> {
                    // skip message like: Analysing type demo.MathGame
                    if (sinkType == SinkType.PROGRESS) {
                        return;
                    }
                    result.append(sinkable);
                };
            }
        };

        CfrDriver driver = new CfrDriver.Builder().withOptions(options).withOutputSink(mySink).build();
        List<String> toAnalyse = new ArrayList<>();
        toAnalyse.add(classFilePath);
        driver.analyse(toAnalyse);

        return result.toString();
    }

}
