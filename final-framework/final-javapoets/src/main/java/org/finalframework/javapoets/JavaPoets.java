package org.finalframework.javapoets;

import com.squareup.javapoet.AnnotationSpec;
import org.finalframework.util.format.LocalDateTimeFormatter;
import org.springframework.lang.NonNull;

import javax.annotation.Generated;
import java.time.LocalDateTime;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/12 20:38:13
 * @since 1.0
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

        static String date() {
            return date(LocalDateTimeFormatter.YYYY_MM_DD_HH_MM_SS.format(LocalDateTime.now()));
        }

        static String date(@NonNull String date) {
            return String.format("@date %s" + LINE, date);
        }

    }


}
