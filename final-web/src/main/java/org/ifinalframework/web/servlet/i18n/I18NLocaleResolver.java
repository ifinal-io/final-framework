/*
 * Copyright 2020-2022 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ifinalframework.web.servlet.i18n;

import lombok.Getter;
import lombok.Setter;
import org.ifinalframework.util.Asserts;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

/**
 * @author ilikly
 * @version 1.0.0
 * @see CookieLocaleResolver
 * @see org.springframework.web.server.i18n.AcceptHeaderLocaleContextResolver
 * @since 1.0.0
 */
@Setter
@Getter
public class I18NLocaleResolver extends CookieLocaleResolver {

    private static final String ACCEPT_LANGUAGE_HEADER = "Accept-Language";

    private final List<Locale> supportedLocales = new ArrayList<>(4);

    private String paramName = "lang";

    private String headerName = "lang";

    @Override
    public Locale resolveLocale(final @NonNull HttpServletRequest request) {

        Locale locale = null;

        String lang = request.getParameter(paramName);
        if (Asserts.nonBlank(lang)) {
            locale = Locale.forLanguageTag(lang);
        }

        if (locale == null && Asserts.nonBlank(headerName)) {
            String header = request.getHeader(headerName);
            if (Asserts.nonBlank(header)) {
                locale = Locale.forLanguageTag(header);
            }
        }

        if (locale == null && request.getHeader(ACCEPT_LANGUAGE_HEADER) != null) {
            locale = request.getLocale();
            if (!isSupportedLocale(locale)) {
                locale = findSupportedLocale(request, locale);
            }
        }

        if (locale == null) {
            locale = super.resolveLocale(request);
        }

        if (!isSupportedLocale(locale)) {
            locale = getDefaultLocale();
        }

        return locale;
    }

    private boolean isSupportedLocale(final Locale locale) {

        return supportedLocales.isEmpty() || supportedLocales.contains(locale);
    }

    private Locale findSupportedLocale(final HttpServletRequest request, final Locale fallback) {

        Enumeration<Locale> requestLocales = request.getLocales();
        while (requestLocales.hasMoreElements()) {
            Locale locale = requestLocales.nextElement();
            if (getSupportedLocales().contains(locale)) {
                return locale;
            }
        }
        return fallback;
    }

    public List<Locale> getSupportedLocales() {
        return this.supportedLocales;
    }

    public void setSupportedLocales(final List<Locale> locales) {

        this.supportedLocales.clear();
        if (locales != null) {
            this.supportedLocales.addAll(locales);
        }
    }

}

