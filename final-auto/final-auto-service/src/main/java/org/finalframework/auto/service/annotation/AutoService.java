package org.finalframework.auto.service.annotation;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.annotation.*;

import static org.finalframework.auto.service.annotation.AutoService.List;


/**
 * 在{@code META-INF}/{@link #path()}目录下生成名为{@link #value()}的文件，用于{@link java.util.ServiceLoader}加载，
 * 内容格式下如下：
 * <ul>
 *     <li>JDK标准格式：{@linkplain Class#getCanonicalName() 目标服务全类名}</li>
 *     <li>命名扩展格式：{@linkplain #name() 服务名}={@linkplain Class#getCanonicalName() 目标服务全类名 </li>
 * </ul>
 *
 * @author likly
 * @version 1.0
 * @date 2020-08-25 19:37:19
 * @since 1.0
 */
@Repeatable(List.class)
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface AutoService {
    @NonNull
    Class<?> value();

    @Nullable
    String name() default "";

    @NonNull
    String path() default "services";

    @Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.CLASS)
    @interface List {
        AutoService[] value();
    }
}
