package org.ifinal.finalframework.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.ifinal.finalframework.annotation.auth.Auth;
import org.ifinal.finalframework.annotation.core.IUser;
import org.ifinal.finalframework.context.user.UserContextHolder;
import org.ifinal.finalframework.util.Reflections;
import org.ifinal.finalframework.web.auth.AuthService;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@Component
@ConditionalOnBean(AuthService.class)
@SuppressWarnings("rawtypes")
public class AuthHandlerInterceptor implements AsyncHandlerInterceptor {


    private final Map<Class<? extends Annotation>, AuthService> authServices = new HashMap<>();

    public AuthHandlerInterceptor(final ObjectProvider<AuthService<?, ?>> authServiceProvider) {

        for (AuthService<?, ?> authService : authServiceProvider) {
            Class<?> targetClass = AopUtils.getTargetClass(authService);
            Class authAnnotation = Reflections.findParameterizedInterfaceArgumentClass(targetClass, AuthService.class, 0);
            logger.info("register auth service of {} for @{}", targetClass, authAnnotation);
            authServices.put(authAnnotation, authService);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean preHandle(final @NonNull HttpServletRequest request, final @NonNull HttpServletResponse response, final @NonNull Object handler) throws Exception {

        Auth auth = findHandlerAuth(handler, Auth.class);

        Annotation authAnnotation = auth;

        if (auth != null) {
            IUser user = UserContextHolder.getUser();
            Class<? extends Annotation> annotation = auth.value();
            if (Auth.class.equals(annotation)) {
                authAnnotation = findHandlerAuth(handler, annotation);
            }
            Objects.requireNonNull(authAnnotation);

            authServices.get(annotation).auth(user, authAnnotation, request, response, handler);
        }


        return true;
    }

    private <A extends Annotation> A findHandlerAuth(final @NonNull Object handler, final Class<A> ann) {

        if (handler instanceof HandlerMethod) {
            A annotation = AnnotatedElementUtils.findMergedAnnotation(((HandlerMethod) handler).getMethod(), ann);
            if (annotation != null) {
                return annotation;
            }
            return AnnotatedElementUtils.findMergedAnnotation(((HandlerMethod) handler).getMethod().getDeclaringClass(), ann);
        }

        return null;
    }

}
