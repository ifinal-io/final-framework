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

import org.benf.cfr.reader.api.OutputSinkFactory;
import org.benf.cfr.reader.api.SinkReturns;
import org.benf.cfr.reader.apiunreleased.ClassFileSource2;
import org.benf.cfr.reader.entities.ClassFile;
import org.benf.cfr.reader.entities.Method;
import org.benf.cfr.reader.mapping.MappingFactory;
import org.benf.cfr.reader.mapping.ObfuscationMapping;
import org.benf.cfr.reader.relationship.MemberNameResolver;
import org.benf.cfr.reader.state.ClassFileSourceImpl;
import org.benf.cfr.reader.state.DCCommonState;
import org.benf.cfr.reader.state.TypeUsageCollectingDumper;
import org.benf.cfr.reader.state.TypeUsageInformation;
import org.benf.cfr.reader.util.CannotLoadClassException;
import org.benf.cfr.reader.util.bytestream.BaseByteData;
import org.benf.cfr.reader.util.collections.ListFactory;
import org.benf.cfr.reader.util.getopt.Options;
import org.benf.cfr.reader.util.getopt.OptionsImpl;
import org.benf.cfr.reader.util.output.Dumper;
import org.benf.cfr.reader.util.output.DumperFactory;
import org.benf.cfr.reader.util.output.IllegalIdentifierDump;
import org.benf.cfr.reader.util.output.NopSummaryDumper;
import org.benf.cfr.reader.util.output.SinkDumperFactory;
import org.benf.cfr.reader.util.output.SummaryDumper;
import org.benf.cfr.reader.util.output.ToStringDumper;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * JadDriver.
 *
 * @author ilikly
 * @version 1.3.1
 * @since 1.3.1
 */
public class JadDriver {

    private Options options;
    private DCCommonState dcCommonState;

    public JadDriver() {
        HashMap<String, String> options = new HashMap<>();
        options.put("showversion", "false");
        options.put("hideutf", "false");
        this.options = new OptionsImpl(options);
        ClassFileSource2 classFileSource = new ClassFileSourceImpl(this.options);
        dcCommonState = new DCCommonState(this.options, classFileSource);
    }

    public String jad(@NonNull Class<?> clazz) {
        return jad(clazz, null);
    }

    public String jad(@NonNull final Class<?> clazz, @Nullable final String methodName) {
        byte[] bytes = DumpDriver.dump(clazz);
        return jad(clazz, bytes, methodName);
    }

    public String jad(String clazz, byte[] bytes, String methodName) throws ClassNotFoundException {
        return jad(ClassUtils.forName(clazz, getClass().getClassLoader()), bytes, methodName);
    }

    public String jad(Class<?> clazz, byte[] bytes, String methodName) {
        return jad(new ClassFile(new BaseByteData(bytes), clazz.getName(), dcCommonState), methodName);
    }

    private String jad(ClassFile c, String methodName) {
        Options options = dcCommonState.getOptions();
        ObfuscationMapping mapping = MappingFactory.get(options, dcCommonState);
        dcCommonState = new DCCommonState(dcCommonState, mapping);

        final StringBuilder sb = new StringBuilder(8192);

        final NavigableMap<Integer, Integer> lineMapping = new TreeMap<Integer, Integer>();

        OutputSinkFactory mySink = new OutputSinkFactory() {
            @Override
            public List<SinkClass> getSupportedSinks(SinkType sinkType, Collection<SinkClass> collection) {
                return Arrays.asList(SinkClass.STRING, SinkClass.DECOMPILED, SinkClass.DECOMPILED_MULTIVER,
                        SinkClass.EXCEPTION_MESSAGE, SinkClass.LINE_NUMBER_MAPPING);
            }

            @Override
            public <T> Sink<T> getSink(final SinkType sinkType, final SinkClass sinkClass) {
                return sinkable -> {
                    // skip message like: Analysing type demo.MathGame
                    if (sinkType == SinkType.PROGRESS) {
                        return;
                    }
                    if (sinkType == SinkType.LINENUMBER) {
                        SinkReturns.LineNumberMapping mapping1 = (SinkReturns.LineNumberMapping) sinkable;
                        NavigableMap<Integer, Integer> classFileMappings = mapping1.getClassFileMappings();
                        NavigableMap<Integer, Integer> mappings = mapping1.getMappings();
                        if (classFileMappings != null && mappings != null) {
                            for (Map.Entry<Integer, Integer> entry : mappings.entrySet()) {
                                Integer srcLineNumber = classFileMappings.get(entry.getKey());
                                lineMapping.put(entry.getValue(), srcLineNumber);
                            }
                        }
                        return;
                    }
                    sb.append(sinkable);
                };
            }
        };


        DumperFactory dumperFactory = new SinkDumperFactory(mySink, options);

        IllegalIdentifierDump illegalIdentifierDump = IllegalIdentifierDump.Factory.get(options);
        Dumper d = new ToStringDumper(); // sentinel dumper.
        try {

            dcCommonState.configureWith(c);
            dumperFactory.getProgressDumper().analysingType(c.getClassType());

            // This may seem odd, but we want to make sure we're analysing the version
            // from the cache, in case that's loaded and tweaked.
            // ClassPathRelocator handles ensuring we don't reload the wrong file.
            try {
                c = dcCommonState.getClassFile(c.getClassType());
            } catch (CannotLoadClassException ignore) {
                // ignore
            }

            if (options.getOption(OptionsImpl.DECOMPILE_INNER_CLASSES)) {
                c.loadInnerClasses(dcCommonState);
            }
            if (options.getOption(OptionsImpl.RENAME_DUP_MEMBERS)) {
                MemberNameResolver.resolveNames(dcCommonState, ListFactory.newList(dcCommonState.getClassCache().getLoadedTypes()));
            }

            TypeUsageCollectingDumper collectingDumper = new TypeUsageCollectingDumper(options, c);
            c.analyseTop(dcCommonState, collectingDumper);

            TypeUsageInformation typeUsageInformation = collectingDumper.getRealTypeUsageInformation();

            SummaryDumper summaryDumper = new NopSummaryDumper();
            d = dumperFactory.getNewTopLevelDumper(c.getClassType(), summaryDumper, typeUsageInformation, illegalIdentifierDump);
            d = dcCommonState.getObfuscationMapping().wrap(d);
            if (options.getOption(OptionsImpl.TRACK_BYTECODE_LOC)) {
                d = dumperFactory.wrapLineNoDumper(d);
            }

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

        } catch (Exception e) {
            throw e;
        } finally {
            if (d != null) {
                d.close();
            }
        }

        return sb.toString();
    }

}
