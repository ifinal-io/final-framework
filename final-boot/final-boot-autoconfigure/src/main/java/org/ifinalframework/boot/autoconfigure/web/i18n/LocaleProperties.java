/*
 * Copyright 2020-2021 the original author or authors.
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

package org.ifinalframework.boot.autoconfigure.web.i18n;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = "final.i18n")
public class LocaleProperties implements Serializable {

    private static final String DEFAULT_LOCALE_PARAM_NAME = "lang";

    private static final String DEFAULT_LOCALE_HEADER_NAME = "lang";

    private static final String DEFAULT_LOCALE_COOKIE_NAME = "locale";

    private static final Integer DEFAULT_LOCALE_COOKIE_MAX_AGE = 24 * 3600;

    private static final String DEFAULT_LOCALE_COOKIE_PATH = "/";

    /**
     * default locale
     *
     * @see Locale#getDefault()
     */
    private Locale defaultLocale = Locale.getDefault();

    private List<Locale> supportedLocales;

    private String paramName = DEFAULT_LOCALE_PARAM_NAME;

    private String headerName = DEFAULT_LOCALE_HEADER_NAME;

    private String cookieName = DEFAULT_LOCALE_COOKIE_NAME;

    private Integer cookieMaxAge = DEFAULT_LOCALE_COOKIE_MAX_AGE;

    private String cookieDomain;

    private String cookiePath = DEFAULT_LOCALE_COOKIE_PATH;

    public Locale getDefaultLocale() {

        return defaultLocale;
    }

    public void setDefaultLocale(final Locale defaultLocale) {

        this.defaultLocale = defaultLocale;
    }

    public List<Locale> getSupportedLocales() {

        return supportedLocales;
    }

    public void setSupportedLocales(final List<Locale> supportedLocales) {

        this.supportedLocales = supportedLocales;
    }

    public String getParamName() {

        return paramName;
    }

    public void setParamName(final String paramName) {

        this.paramName = paramName;
    }

    public String getHeaderName() {

        return headerName;
    }

    public void setHeaderName(final String headerName) {

        this.headerName = headerName;
    }

    public String getCookieName() {

        return cookieName;
    }

    public void setCookieName(final String cookieName) {

        this.cookieName = cookieName;
    }

    public Integer getCookieMaxAge() {

        return cookieMaxAge;
    }

    public void setCookieMaxAge(final Integer cookieMaxAge) {

        this.cookieMaxAge = cookieMaxAge;
    }

    public String getCookieDomain() {

        return cookieDomain;
    }

    public void setCookieDomain(final String cookieDomain) {

        this.cookieDomain = cookieDomain;
    }

    public String getCookiePath() {

        return cookiePath;
    }

    public void setCookiePath(final String cookiePath) {

        this.cookiePath = cookiePath;
    }

}

