package org.ifinal.finalframework.web.auth;

import java.lang.annotation.Annotation;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.ifinal.finalframework.annotation.auth.Auth;
import org.ifinal.finalframework.annotation.core.IUser;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0.0
 * @see Auth
 * @since 1.0.0
 */
@FunctionalInterface
public interface AuthService<T extends IUser<?>, A extends Annotation> {

    /**
     * @param user     the {@link IUser} login, maybe null.
     * @param auth     the {@link Annotation} link {@link Auth}
     * @param request  the {@link HttpServletRequest}
     * @param response the {@link HttpServletResponse}
     * @param handler  the handler
     */
    default void auth(@Nullable T user, @NonNull A auth, @Nullable HttpServletRequest request,
        @Nullable HttpServletResponse response,
        @NonNull Object handler) {
        auth(user, auth, handler);
    }

    /**
     * @param user user
     * @param auth auth
     */
    void auth(@Nullable T user, @NonNull A auth, @NonNull Object handler);

}
