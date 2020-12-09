package org.ifinal.finalframework.data.annotation;

/**
 * 引用模式
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public enum ReferenceMode {
    /**
     * 简单模式，{@literal id} 引用简写为引用属性名
     * <pre>
     *     <code>
     *          Bean.id ==&gt; bean
     *     </code>
     * </pre>
     */
    SIMPLE,
    /**
     * 典范模式，与其他引用一样，{@literal id} 将与引用属性拼接
     * <pre>
     *     <code>
     *         Bean.id ==&gt; beanId
     *     </code>
     * </pre>
     */
    CANONICAL
}
