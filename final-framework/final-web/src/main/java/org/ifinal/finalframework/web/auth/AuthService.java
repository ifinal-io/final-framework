package org.ifinal.finalframework.web.auth;

import org.ifinal.finalframework.annotation.IUser;
import org.ifinal.finalframework.annotation.auth.Auth;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;

/**
 * @author likly
 * @version 1.0.0
 * @see Auth
 * @since 1.0.0
 */
@FunctionalInterface
public interface AuthService<A extends Annotation> {

    /**
     * @param user     the {@link IUser} login, maybe null.
     * @param auth     the {@link Annotation} link {@link Auth}
     * @param request  the {@link HttpServletRequest}
     * @param response the {@link HttpServletResponse}
     * @param handler  the handler
     */
    default void auth(@Nullable IUser<?> user, @NonNull A auth, @Nullable HttpServletRequest request, @Nullable HttpServletResponse response, @NonNull Object handler) {
        auth(user, auth, handler);
    }


    /**
     * @param user user
     * @param auth auth
     */
    void auth(@Nullable IUser<?> user, @NonNull A auth, @NonNull Object handler);


}
