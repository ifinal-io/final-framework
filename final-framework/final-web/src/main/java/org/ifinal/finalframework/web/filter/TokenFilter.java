package org.ifinal.finalframework.web.filter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import org.ifinal.finalframework.context.user.UserContextHolder;
import org.ifinal.finalframework.core.annotation.IUser;
import org.ifinal.finalframework.web.auth.TokenService;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
@ConditionalOnBean(TokenService.class)
public class TokenFilter extends OncePerRequestFilter {

    @Resource
    @SuppressWarnings("rawtypes")
    private TokenService<? extends IUser> tokenService;

    @Override
    protected void doFilterInternal(final @NonNull HttpServletRequest request,
        final @NonNull HttpServletResponse response,
        final @NonNull FilterChain filterChain) throws ServletException, IOException {

        UserContextHolder.setUser(tokenService.token(request, response), true);
        filterChain.doFilter(request, response);

    }

}
