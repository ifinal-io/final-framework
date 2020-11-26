package org.ifinal.finalframework.context.user;


import org.ifinal.finalframework.annotation.IUser;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
class SimpleUserContext<T extends IUser<?>> implements UserContext<T> {
    private final T user;

    public SimpleUserContext(T user) {
        this.user = user;
    }

    @Override
    public T getUser() {
        return user;
    }
}

