package org.ifinal.finalframework.javapoets;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import org.ifinal.finalframework.util.format.LocalDateTimeFormatter;
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
 * @author likly
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

    interface JavaDoc {

        static String author() {
            return author("finalframework");
        }

        static String author(String author) {
            return "@author " + author + LINE;
        }

        static String version() {
            return version("1.0.0");
        }

        static String version(String version) {
            return "@version " + version + LINE;
        }


    }


}
