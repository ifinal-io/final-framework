package org.finalframework.example.web.service;

import lombok.extern.slf4j.Slf4j;
import org.finalframework.annotation.IUser;
import org.finalframework.annotation.auth.Auth;
import org.finalframework.context.exception.ForbiddenException;
import org.finalframework.web.auth.AuthService;
import org.finalframework.web.auth.TokenService;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/24 14:45:35
 * @since 1.0
 */
@Slf4j
@Component
public class AuthTokenService implements TokenService, AuthService<Auth> {
    @Override
    public void auth(@Nullable IUser user, @NonNull Auth auth) {
        logger.info("try auth ...");

        if (user == null) {
            throw new ForbiddenException("未登录！");
        }

    }

    @Nullable
    @Override
    public IUser token(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response) {
        logger.info("try token");


        return new IUser() {
            @Override
            public String getName() {
                return "user";
            }

            @Override
            public void setName(String name) {

            }

            @Override
            public Serializable getId() {
                return 1L;
            }

            @Override
            public void setId(Serializable serializable) {

            }
        };
    }
}
