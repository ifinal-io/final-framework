

package org.finalframework.annotation;


import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-26 20:57
 * @since 1.0
 */
public interface IException {

    /**
     * return the exception code.
     *
     * @return the exception code.
     */
    @NonNull
    String getCode();

    /**
     * return the exception message for code.
     *
     * @return the exception message for code.
     */
    @NonNull
    String getMessage();

}
