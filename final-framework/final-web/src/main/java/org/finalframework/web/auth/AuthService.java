package org.finalframework.web.auth;

import org.finalframework.annotation.IUser;
import org.finalframework.annotation.auth.Auth;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.annotation.Annotation;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/15 16:11:06
 * @see Auth
 * @since 1.0
 */
@SuppressWarnings("rawtypes")
@FunctionalInterface
public interface AuthService<A extends Annotation> {


    /**
     * @param user
     * @param auth
     * @throws org.finalframework.context.exception.ForbiddenException
     */
    void auth(@Nullable IUser<?> user, @NonNull A auth);


}
