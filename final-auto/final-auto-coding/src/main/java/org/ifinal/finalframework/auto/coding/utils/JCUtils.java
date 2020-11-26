package org.ifinal.finalframework.auto.coding.utils;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

/**
 * Java 编译工具集
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface JCUtils {


    /**
     * 如果指的{@link TypeElement}是{@link ElementKind#ENUM}，则返回 {@code true}。
     *
     * @param element 指定的 {@link TypeElement}
     * @return {@code true} if the test {@link TypeElement}'s kind is {@link ElementKind#ENUM}
     */
    static boolean isEnum(TypeElement element) {
        return element.getKind() == ElementKind.ENUM;
    }


}
