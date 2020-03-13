package org.finalframework.data.user;


import org.finalframework.data.entity.IUser;
import org.springframework.core.NamedInheritableThreadLocal;
import org.springframework.core.NamedThreadLocal;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-13 13:32:34
 * @since 1.0
 */
public class UserContextHolder {
    private static final ThreadLocal<UserContext<? extends IUser<?>>> userContextHolder =
            new NamedThreadLocal<>("UserContext");

    private static final ThreadLocal<UserContext<? extends IUser<?>>> inheritableUserContextHolder =
            new NamedInheritableThreadLocal<>("UserContext");

    private static IUser<?> defaultUser;

    public static void resetUserContext() {
        userContextHolder.remove();
    }

    public static void setUserContext(@Nullable UserContext<? extends IUser<?>> localeContext, boolean inheritable) {
        if (localeContext == null) {
            resetUserContext();
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
    public static UserContext<? extends IUser<?>> getUserContext() {
        UserContext<? extends IUser<?>> userContext = userContextHolder.get();
        if (userContext == null) {
            userContext = inheritableUserContextHolder.get();
        }
        return userContext;
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

