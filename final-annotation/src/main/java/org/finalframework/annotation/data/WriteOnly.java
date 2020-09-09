

package org.finalframework.annotation.data;

/**
 * Mark the element is a {@link WriteOnly} property which will not be generated {@literal select}.
 *
 * @author likly
 * @version 1.0
 * @date 2018-10-15 15:14
 * @since 1.0
 */
@Column
@Documented
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface WriteOnly {

}
