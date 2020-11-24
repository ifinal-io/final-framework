package org.finalframework.web.auth;

import org.finalframework.annotation.IUser;
import org.finalframework.annotation.auth.Auth;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
     * @param user     the {@link IUser} login, maybe null.
     * @param auth     the {@link Annotation} link {@link Auth}
     * @param request  the {@link HttpServletRequest}
     * @param response the {@link HttpServletResponse}
     * @param handler  the handler
     * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(HttpServletRequest, HttpServletResponse, Object)
     */
    default void auth(@Nullable IUser<?> user, @NonNull A auth, @NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        auth(user, auth);
    }


    /**
     * @param user
     * @param auth
     * @throws org.finalframework.context.exception.ForbiddenException
     */
    void auth(@Nullable IUser<?> user, @NonNull A auth);


}
