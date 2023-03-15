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

package org.ifinalframework.javapoets;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import org.ifinalframework.util.JarVersions;
import org.ifinalframework.util.format.LocalDateTimeFormatter;
import org.springframework.lang.NonNull;

import javax.annotation.Generated;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * <ul>
 *     <li>when you want a {@link Class} do with {@link TypeSpec#classBuilder(String)}</li>
 *     <li>when you want a {@linkplain Class interface} do with {@link TypeSpec#interfaceBuilder(String)}</li>
 *     <li>when you want a {@link Annotation} do with {@link TypeSpec#annotationBuilder(String)}</li>
 *     <li>when you want a {@link Enum} do with {@link TypeSpec#enumBuilder(String)}</li>
 *     <li>when you want a {@link Field} do with {@link FieldSpec}</li>
 *     <li>when you want a {@link Constructor} do with {@link MethodSpec#constructorBuilder()}</li>
 *     <li>when you want a {@link Method} do with {@link MethodSpec#methodBuilder(String)} ()}</li>
 * </ul>
 *
 * @author ilikly
 * @version 1.0.0
 * @see TypeSpec
 * @see AnnotationSpec
 * @see MethodSpec
 * @see FieldSpec
 * @since 1.0.0
 */
public interface JavaPoets {

    String LINE = "\n";

    static AnnotationSpec generated(@NonNull Class<?> generator) {
        return AnnotationSpec.builder(Generated.class)
            .addMember("value", "$S", generator.getCanonicalName())
            .addMember("date", "$S", LocalDateTimeFormatter.YYYY_MM_DD_HH_MM_SS.format(LocalDateTime.now()))
            .build();
    }

    /**
     * Javadoc.
     */
    interface Javadoc {

        static String author() {
            return author("finalframework");
        }

        static String author(String author) {
            return "@author " + author + LINE;
        }

        static String version() {
            return version(JarVersions.getVersion(JavaPoets.class));
        }

        static String version(String version) {
            return "@version " + version + LINE;
        }

    }

}
