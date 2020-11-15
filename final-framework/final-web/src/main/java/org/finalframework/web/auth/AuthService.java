package org.finalframework.web.auth;

import org.finalframework.annotation.IUser;
import org.finalframework.annotation.auth.Auth;
import org.springframework.lang.NonNull;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/15 16:11:06
 * @since 1.0
 */
@SuppressWarnings("rawtypes")
public interface AuthService<T extends IUser> {

    /**
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws org.finalframework.context.exception.UnauthorizedException
     */
    @NonNull
    T login(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler);

    /**
     * @param user
     * @param auth
     * @throws org.finalframework.context.exception.ForbiddenException
     */
    void auth(@NonNull T user, @NonNull Auth auth);


}
