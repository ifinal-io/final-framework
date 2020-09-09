

package org.finalframework.annotation.data;

/**
 * 引用模式
 *
 * @author likly
 * @version 1.0
 * @date 2019-03-07 13:38:11
 * @since 1.0
 */
public enum ReferenceMode {
    /**
     * 简单模式，{@literal id} 引用简写为引用属性名
     * <pre>
     *     <code>
     *          Bean.id ==> bean
     *     </code>
     * </pre>
     */
    SIMPLE,
    /**
     * 典范模式，与其他引用一样，{@literal id} 将与引用属性拼接
     * <pre>
     *     <code>
     *         Bean.id ==> beanId
     *     </code>
     * </pre>
     */
    CANONICAL
}
