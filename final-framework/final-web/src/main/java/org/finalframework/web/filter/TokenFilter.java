package org.finalframework.web.filter;

import org.finalframework.context.user.UserContextHolder;
import org.finalframework.web.auth.TokenService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/24 13:28:40
 * @since 1.0
 */
@Component
@ConditionalOnBean(TokenService.class)
public class TokenFilter extends OncePerRequestFilter {

    @Resource
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        UserContextHolder.setUser(tokenService.token(request, response), true);
        filterChain.doFilter(request, response);

    }
}
