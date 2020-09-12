

package org.finalframework.context.user;


import org.finalframework.annotation.IUser;
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
public final class UserContextHolder {
    private static final ThreadLocal<UserContext<? extends IUser<?>>> USER_CONTEXT_HOLDER =
            new NamedThreadLocal<>("UserContext");

    private static final ThreadLocal<UserContext<? extends IUser<?>>> INHERITABLE_USER_CONTEXT_HOLDER =
            new NamedInheritableThreadLocal<>("UserContext");

    private static IUser<?> defaultUser;

    private UserContextHolder() {
    }

    public static void reset() {
        USER_CONTEXT_HOLDER.remove();
        INHERITABLE_USER_CONTEXT_HOLDER.remove();
    }

    public static void setUserContext(@Nullable UserContext<? extends IUser<?>> localeContext, boolean inheritable) {
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

    @Nullable
    @SuppressWarnings("unchecked")
    public static <T extends IUser<?>> UserContext<T> getUserContext() {
        UserContext<? extends IUser<?>> userContext = USER_CONTEXT_HOLDER.get();
        if (userContext == null) {
            userContext = INHERITABLE_USER_CONTEXT_HOLDER.get();
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

    /**
     * return {@code ture} if user have login, otherwise {@code false}.
     *
     * @return {@code ture} if user have login, otherwise {@code false}.
     */
    public static boolean isLogin() {
        return getUser() != null;
    }

}

