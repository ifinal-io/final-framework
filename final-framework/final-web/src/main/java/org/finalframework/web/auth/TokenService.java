package org.finalframework.web.auth;

import org.finalframework.annotation.IUser;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Just only do parse token from the {@link HttpServletRequest} to {@link IUser} but don't check the user info.
 *
 * @author likly
 * @version 1.0
 * @date 2020/11/24 13:33:22
 * @since 1.0
 */
@SuppressWarnings("rawtypes")
@FunctionalInterface
public interface TokenService {

    @Nullable
    IUser token(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response);

}
