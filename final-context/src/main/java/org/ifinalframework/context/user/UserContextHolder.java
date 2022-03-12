/*
 * Copyright 2020-2021 the original author or authors.
 *
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

package org.ifinalframework.context.user;

import org.springframework.core.NamedInheritableThreadLocal;
import org.springframework.core.NamedThreadLocal;
import org.springframework.lang.Nullable;

import org.ifinalframework.core.IUser;

/**
 * The context holder of {@link UserContext} with {@link ThreadLocal}.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("unchecked")
public final class UserContextHolder {

    /**
     * the user context name.
     */
    private static final String USER_CONTEXT_NAME = "UserContext";

    /**
     * the user context in thread local.
     */
    private static final ThreadLocal<UserContext<? extends IUser<?>>> USER_CONTEXT_HOLDER =
        new NamedThreadLocal<>(USER_CONTEXT_NAME);

    /**
     * the user context in inheritable thread local
     */
    private static final ThreadLocal<UserContext<? extends IUser<?>>> INHERITABLE_USER_CONTEXT_HOLDER =
        new NamedInheritableThreadLocal<>(USER_CONTEXT_NAME);

    /**
     * the default user
     */
    private static IUser<?> defaultUser;

    private UserContextHolder() {
    }

    public static void reset() {
        USER_CONTEXT_HOLDER.remove();
        INHERITABLE_USER_CONTEXT_HOLDER.remove();
    }

    public static void setUserContext(final @Nullable UserContext<? extends IUser<?>> localeContext,
        final boolean inheritable) {

        if (localeContext == null) {
            reset();
        } else {
            if (inheritable) {
                INHERITABLE_USER_CONTEXT_HOLDER.set(localeContext);
                USER_CONTEXT_HOLDER.remove();
            } else {
                USER_CONTEXT_HOLDER.set(localeContext);
                INHERITABLE_USER_CONTEXT_HOLDER.remove();
            }
        }
    }

    /**
     * set user context
     */
    public static void setUserContext(final @Nullable UserContext<? extends IUser<?>> userContext) {
        setUserContext(userContext, false);
    }

    /**
     * set default user
     */
    public static <T extends IUser<?>> void setDefaultUser(final @Nullable T user) {UserContextHolder.defaultUser = user;
    }

    @Nullable
    public static <T extends IUser<?>> UserContext<T> getUserContext() {
        UserContext<? extends IUser<?>> userContext = USER_CONTEXT_HOLDER.get();
        if (userContext == null) {
            userContext = INHERITABLE_USER_CONTEXT_HOLDER.get();
        }
        return (UserContext<T>) userContext;
    }

    public static <T extends IUser<?>> T getUser(final @Nullable UserContext<? extends IUser<?>> localeContext) {

        if (localeContext != null) {
            final IUser<?> locale = localeContext.getUser();
            if (locale != null) {
                return (T) locale;
            }
        }
        return (T) defaultUser;
    }

    @Nullable
    public static <T extends IUser<?>> T getUser() {
        return getUser(getUserContext());
    }

    public static <T extends IUser<?>> void setUser(final @Nullable T user, final boolean inheritable) {

        UserContext<?> userContext = getUserContext();
        if (userContext == null) {
            userContext = new SimpleUserContext<>(user);
        }
        setUserContext(userContext, inheritable);
    }

    public static void setUser(final @Nullable IUser<?> user) {
        setUser(user, false);
    }

    /**
     * return {@code ture} if user have login, otherwise {@code false}.
     *
     * @return {@code ture} if user have login, otherwise {@code false}.
     */
    public static boolean isLogin() {
        return getUser() != null;
    }

}

