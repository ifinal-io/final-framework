package org.ifinal.finalframework.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Collection;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0.0
 * @see AnnotatedElementUtils
 * @since 1.0.0
 */
public final class AnnotatedElements {

    private AnnotatedElements() {
    }

    /**
     * 查找所有声明在{@link AnnotatedElement}上的{@link Annotation},包含声明在其父类或接口上的。
     *
     * @param ae      元素
     * @param annType 注解类型
     * @param <A>     注解限定
     * @return Annotations
     */
    public static <A extends Annotation> Collection<A> findAllAnnotations(final @NonNull AnnotatedElement ae,
        final @NonNull Class<A> annType) {

        return AnnotatedElementUtils.findAllMergedAnnotations(ae, annType);
    }

    /**
     * 查找所有声明在{@link AnnotatedElement}上的{@link Annotation},不包含声明在其父类或接口上的。
     *
     * @param ae      元素
     * @param annType 注解类型
     * @param <A>     注解限定
     * @return Annotations
     */
    public static <A extends Annotation> Collection<A> getAllLocalAnnotations(final @NonNull AnnotatedElement ae,
        final @NonNull Class<A> annType) {

        return AnnotatedElementUtils.getAllMergedAnnotations(ae, annType);
    }

    public boolean isAnnotated(final @NonNull AnnotatedElement ae, final @NonNull String annName) {

        return AnnotatedElementUtils.isAnnotated(ae, annName);
    }

    public boolean isAnnotated(final @NonNull AnnotatedElement ae, final @NonNull Class<? extends Annotation> annType) {

        return AnnotatedElementUtils.isAnnotated(ae, annType);
    }

}
