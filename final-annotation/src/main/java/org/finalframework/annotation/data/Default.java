

package org.finalframework.annotation.data;

/**
 * Marked the element is not need to {@literal insert}. The element has a default value. Such as a {@link Created}
 * column when it default {@literal now()}.
 *
 * @author likly
 * @version 1.0
 * @date 2020-03-15 22:59:10
 * @see PrimaryKey
 * @see Version
 * @see Created
 * @see LastModified
 * @since 1.0
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Default {

}
