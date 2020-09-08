/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.finalframework.spring.web.i18n;


import org.finalframework.core.Asserts;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-12 12:21:41
 * @since 1.0
 */
public class I18NLocaleResolver extends CookieLocaleResolver {

    private static final String ACCEPT_LANGUAGE_HEADER = "Accept-Language";
    private final List<Locale> supportedLocales = new ArrayList<Locale>(4);

    private String paramName;
    private String headerName;

    @Override
    public Locale resolveLocale(HttpServletRequest request) {

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

        if (locale == null && request.getHeader("Accept-Language") != null) {
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

    private boolean isSupportedLocale(Locale locale) {
        List<Locale> supportedLocales = getSupportedLocales();
        return (supportedLocales.isEmpty() || supportedLocales.contains(locale));
    }

    private Locale findSupportedLocale(HttpServletRequest request, Locale fallback) {
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

    public void setSupportedLocales(List<Locale> locales) {
        this.supportedLocales.clear();
        if (locales != null) {
            this.supportedLocales.addAll(locales);
        }
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }
}

