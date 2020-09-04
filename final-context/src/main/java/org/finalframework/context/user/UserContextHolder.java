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

package org.finalframework.context.user;


import org.finalframework.data.annotation.IUser;
import org.springframework.core.NamedInheritableThreadLocal;
import org.springframework.core.NamedThreadLocal;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-13 13:32:34
 * @see org.springframework.context.i18n.LocaleContextHolder
 * @since 1.0
 */
@SuppressWarnings("unused")
public final class UserContextHolder {
    private static final ThreadLocal<UserContext<? extends IUser<?>>> userContextHolder =
            new NamedThreadLocal<>("UserContext");

    private static final ThreadLocal<UserContext<? extends IUser<?>>> inheritableUserContextHolder =
            new NamedInheritableThreadLocal<>("UserContext");

    private static IUser<?> defaultUser;

    private UserContextHolder() {
    }

    public static void reset() {
        userContextHolder.remove();
        inheritableUserContextHolder.remove();
    }

    public static void setUserContext(@Nullable UserContext<? extends IUser<?>> localeContext, boolean inheritable) {
        if (localeContext == null) {
            reset();
        } else {
            if (inheritable) {
                inheritableUserContextHolder.set(localeContext);
                userContextHolder.remove();
            } else {
                userContextHolder.set(localeContext);
                inheritableUserContextHolder.remove();
            }
        }
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public static <T extends IUser<?>> UserContext<T> getUserContext() {
        UserContext<? extends IUser<?>> userContext = userContextHolder.get();
        if (userContext == null) {
            userContext = inheritableUserContextHolder.get();
        }
        return (UserContext<T>) userContext;
    }

    public static void setUserContext(@Nullable UserContext<? extends IUser<?>> localeContext) {
        setUserContext(localeContext, false);
    }

    public static <T extends IUser<?>> void setUser(@Nullable T user, boolean inheritable) {
        UserContext<?> userContext = getUserContext();
        if (userContext == null) {
            userContext = new SimpleUserContext<>(user);
        }
        setUserContext(userContext, inheritable);
    }

    public static <T extends IUser<?>> void setDefaultUser(@Nullable T user) {
        UserContextHolder.defaultUser = user;
    }

    public static <T extends IUser<?>> T getUser() {
        return getUser(getUserContext());
    }

    public static void setUser(@Nullable IUser<?> user) {
        setUser(user, false);
    }

    @SuppressWarnings("unchecked")
    public static <T extends IUser<?>> T getUser(@Nullable UserContext<? extends IUser<?>> localeContext) {
        if (localeContext != null) {
            IUser<?> locale = localeContext.getUser();
            if (locale != null) {
                return (T) locale;
            }
        }
        return (T) defaultUser;
    }

}

