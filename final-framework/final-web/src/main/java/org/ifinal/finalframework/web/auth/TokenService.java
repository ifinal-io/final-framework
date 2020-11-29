package org.ifinal.finalframework.web.auth;

import org.ifinal.finalframework.annotation.IUser;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Just only do parse token from the {@link HttpServletRequest} to {@link IUser} but don't check the user info.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@FunctionalInterface
public interface TokenService<T extends IUser<?>> {

    /**
     * return the {@link IUser} which parsed from the {@link HttpServletRequest}.
     *
     * @param request  http request
     * @param response http response
     * @return the {@link IUser}
     */
    @Nullable
    T token(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response);

}
