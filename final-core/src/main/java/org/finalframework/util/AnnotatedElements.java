package org.finalframework.util;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.NonNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2020/10/27 03:36:40
 * @see AnnotatedElementUtils
 * @since 1.0
 */
public final class AnnotatedElements {

    private AnnotatedElements() {
    }

    public boolean isAnnotated(@NonNull AnnotatedElement ae, @NonNull String annName) {
        return AnnotatedElementUtils.isAnnotated(ae, annName);
    }

    public boolean isAnnotated(@NonNull AnnotatedElement ae, @NonNull Class<? extends Annotation> annType) {
        return AnnotatedElementUtils.isAnnotated(ae, annType);
    }

    /**
     * 查找所有声明在{@link AnnotatedElement}上的{@link Annotation},包含声明在其父类或接口上的。
     *
     * @param ae      元素
     * @param annType 注解类型
     * @param <A>     注解限定
     */
    public static <A extends Annotation> Collection<A> findAllAnnotations(@NonNull AnnotatedElement ae, @NonNull Class<A> annType) {
        return AnnotatedElementUtils.findAllMergedAnnotations(ae, annType);
    }

    /**
     * 查找所有声明在{@link AnnotatedElement}上的{@link Annotation},不包含声明在其父类或接口上的。
     *
     * @param ae      元素
     * @param annType 注解类型
     * @param <A>     注解限定
     */
    public static <A extends Annotation> Collection<A> getAllLocalAnnotations(@NonNull AnnotatedElement ae, @NonNull Class<A> annType) {
        return AnnotatedElementUtils.getAllMergedAnnotations(ae, annType);
    }


}
