package org.finalframework.web.interceptor;

import org.finalframework.annotation.IUser;
import org.finalframework.annotation.auth.Auth;
import org.finalframework.web.auth.AuthService;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.NonNull;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/15 16:16:17
 * @since 1.0
 */
//@HandlerInterceptor
@SuppressWarnings("rawtypes")
public class AuthHandlerInterceptor implements AsyncHandlerInterceptor {

    private final AuthService<IUser> authService;

    public AuthHandlerInterceptor(AuthService<IUser> authService) {
        this.authService = authService;
    }


    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, Object handler) throws Exception {

        Auth auth = findHandlerAuth(handler);

        if (auth != null) {
            IUser user = authService.login(request, response, handler);
            authService.auth(user, auth);
        }


        return true;
    }

    private Auth findHandlerAuth(@NonNull Object handler) {
        if (handler instanceof HandlerMethod) {
            Auth auth = ((HandlerMethod) handler).getMethodAnnotation(Auth.class);
            if (auth != null) {
                return auth;
            }
            return AnnotatedElementUtils.findMergedAnnotation(((HandlerMethod) handler).getMethod().getDeclaringClass(), Auth.class);
        }

        return null;
    }

}
