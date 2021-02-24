package org.ifinal.finalframework.security.config;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * SecurityConfig.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(final HttpSecurity http) throws Exception {


        Map<RequestMappingInfo, HandlerMethod> handlerMethods = getApplicationContext().getBean(RequestMappingHandlerMapping.class)
            .getHandlerMethods();

        Set<String> anonymousUrls = new HashSet<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> infoEntry : handlerMethods.entrySet()) {
            HandlerMethod handlerMethod = infoEntry.getValue();
            if (handlerMethod.hasMethodAnnotation(PreAuthorize.class)) {
                anonymousUrls.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
            }
        }

        http.authorizeRequests()
            .antMatchers(anonymousUrls.toArray(new String[0]))
            .authenticated()
            .and().formLogin().permitAll()
            .and().logout().permitAll();

    }

}
