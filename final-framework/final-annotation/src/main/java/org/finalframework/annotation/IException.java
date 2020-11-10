package org.finalframework.annotation;


import org.springframework.lang.NonNull;

/**
 * The interface which a service {@link Exception} should be impl. The exception {@linkplain #getCode()} and {@linkplain #getMessage()}
 * should be injected into {@linkplain IResult result}.
 *
 * @author likly
 * @version 1.0
 * @date 2018-09-26 20:57
 * @see IResult
 * @see Exception
 * @since 1.0
 */
public interface IException {

    /**
     * return the exception code.
     *
     * @return the exception code.
     * @see IResult#getCode()
     */
    @NonNull
    String getCode();

    /**
     * return the exception message for code.
     *
     * @return the exception message for code.
     * @see IResult#getMessage()
     * @see Exception#getMessage()
     */
    @NonNull
    String getMessage();

}
