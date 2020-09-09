

package org.finalframework.context.user;


import org.finalframework.annotation.IUser;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-13 13:49:08
 * @since 1.0
 */
public class SimpleUserContext<T extends IUser<?>> implements UserContext<T> {
    private final T user;

    public SimpleUserContext(T user) {
        this.user = user;
    }

    @Override
    public T getUser() {
        return user;
    }
}

